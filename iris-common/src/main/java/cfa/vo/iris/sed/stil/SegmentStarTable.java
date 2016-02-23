/**
 * Copyright (C) 2016 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cfa.vo.iris.sed.stil;

import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import uk.ac.starlink.table.ColumnInfo;
import uk.ac.starlink.table.ColumnStarTable;
import uk.ac.starlink.table.PrimitiveArrayColumn;
import cfa.vo.iris.units.UnitsException;
import cfa.vo.iris.units.UnitsManager;
import cfa.vo.iris.units.XUnit;
import cfa.vo.iris.units.YUnit;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.common.SedInconsistentException;
import cfa.vo.sedlib.common.SedNoDataException;
import cfa.vo.sedlib.common.Utypes;
import cfa.vo.utils.Default;

/**
 * StarTable implementation based on the data contained in an SED segment - designed 
 * specifically for use in plotting applications. The Star table will have at least 2 and 
 * no more than 6 columns representing spectral and flux values and error ranges.
 * 
 * TODO: This should ultimately accept any primitive array data and units and manage its data
 *   accordingly.
 *
 */
public class SegmentStarTable extends ColumnStarTable {
    
    private static final Logger logger = Logger.getLogger(SegmentStarTable.class.getName());
    private static final UnitsManager units = Default.getInstance().getUnitsManager();
    
    private Segment segment;
    private XUnit specUnits;
    private YUnit fluxUnits;

    private double[] specValues;
    private double[] fluxValues;

    private double[] specErrValues;
    private double[] specErrValuesLo;
    private double[] fluxErrValues;
    private double[] fluxErrValuesLo;
    
    public SegmentStarTable(Segment segment) 
            throws SedNoDataException, UnitsException, SedInconsistentException {
        this(segment, null);
    }
    
    public SegmentStarTable(Segment segment, String id) 
            throws UnitsException, SedNoDataException, SedInconsistentException 
    {
        this.segment = segment;
        this.specUnits = units.newXUnits(segment.getSpectralAxisUnits());
        this.fluxUnits = units.newYUnits(segment.getFluxAxisUnits());
        this.setName(id);
        
        // Look for a name in the segment if we are not given one
        if (StringUtils.isBlank(id)) {
            if (segment.isSetTarget() && segment.getTarget().isSetName()) {
                setName(segment.getTarget().getName().getValue());
            } else {
                setName(UUID.randomUUID().toString());
            }
        }
        
        setSpecValues(segment.getSpectralAxisValues());
        setFluxValues(segment.getFluxAxisValues());
        
        // Try to add flux error values
        this.fluxErrValues = (double[]) getDataFromSegment(Utypes.SEG_DATA_FLUXAXIS_ACC_STATERR);
        if (isEmpty(fluxErrValues)) {
            fluxErrValues = (double[]) getDataFromSegment(Utypes.SEG_DATA_FLUXAXIS_ACC_STATERRHIGH);
            fluxErrValuesLo = (double[]) getDataFromSegment(Utypes.SEG_DATA_FLUXAXIS_ACC_STATERRLOW);
        }
        setFluxErrValues(fluxErrValues);
        setFluxErrValuesLo(fluxErrValuesLo);
        
        // Try to add spectral error columns
        specErrValues = (double[]) getDataFromSegment(Utypes.SEG_DATA_SPECTRALAXIS_ACC_STATERR);
        if (isEmpty(specErrValues)) {
            specErrValues = (double[]) getDataFromSegment(Utypes.SEG_DATA_SPECTRALAXIS_ACC_STATERRHIGH);
            specErrValuesLo = (double[]) getDataFromSegment(Utypes.SEG_DATA_SPECTRALAXIS_ACC_STATERRLOW);
        }
        setSpecErrValues(specErrValues);
        setSpecErrValuesLo(specErrValuesLo);
    }

    private double[] getDataFromSegment(int utype) {
        double[] ret = null;
        try {
            ret = (double[]) segment.getData().getDataValues(utype);
        } catch (SedNoDataException | SedInconsistentException e) {
            logger.fine("Cannot read data for segment for utype: " + e.getLocalizedMessage());
        }
        return ret;
    }

    @Override
    public long getRowCount() {
        return segment.getData().getLength();
    }

    // TODO: Make sure this logic makes sense W.R.T. what's in the Units package.
    public void setSpecUnits(XUnit newUnit) throws UnitsException {
        if (newUnit.equals(this.specUnits)) return;
        
        setSpecValues(units.convertX(specValues, specUnits, newUnit));
        setSpecErrValues(units.convertX(specErrValues, specUnits, newUnit));
        setSpecErrValuesLo(units.convertX(specErrValuesLo, specUnits, newUnit));
        specUnits = newUnit;
        
        // This may change Y values, so update them accordingly.
        setFluxUnits(fluxUnits);
    }
    
    public void setFluxUnits(YUnit newUnit) throws UnitsException {
        if (specValues == null) return;
        
        if (fluxValues != null) {
            setFluxValues(units.convertY(fluxValues, specValues, fluxUnits, specUnits, newUnit));
        }
        if (fluxErrValues != null) {
            setFluxErrValues(units.convertY(fluxErrValues, specValues, fluxUnits, specUnits, newUnit));
        }
        if (fluxErrValuesLo != null) {
            setFluxErrValuesLo(units.convertY(fluxErrValuesLo, specValues, fluxUnits, specUnits, newUnit));
        }
        
        fluxUnits = newUnit;
    }

    public XUnit getSpecUnits() {
        return specUnits;
    }

    public YUnit getFluxUnits() {
        return fluxUnits;
    }

    /*
     * Setters overwrite existing column data, package protected at the moment,
     * but in the future we may want to be able to manually specify array data to
     * plot.
     * 
     */
    
    void setSpecValues(double[] specValues) {
        removeColumn(Column.SPECTRAL_COL);
        this.specValues = specValues;
        
        if (!isEmpty(specValues))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.SPECTRAL_COL.getColumnInfo(), (Object) specValues));
    }

    void setFluxValues(double[] fluxValues) {
        removeColumn(Column.FLUX_COL);
        this.fluxValues = fluxValues;
        
        if (!isEmpty(fluxValues))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.FLUX_COL.getColumnInfo(), (Object) fluxValues));
    }

    void setSpecErrValues(double[] specErrValues) {
        removeColumn(Column.SPECTRAL_ERR_HI);
        this.specErrValues = specErrValues;
        
        if (!isEmpty(specErrValues))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.SPECTRAL_ERR_HI.getColumnInfo(), (Object) specErrValues));
    }

    void setSpecErrValuesLo(double[] specErrValuesLo) {
        removeColumn(Column.SPECTRAL_ERR_LO);
        this.specErrValuesLo = specErrValuesLo;
        
        if (!isEmpty(specErrValuesLo))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.SPECTRAL_ERR_LO.getColumnInfo(), (Object) specErrValuesLo));
    }

    void setFluxErrValues(double[] fluxErrValues) {
        removeColumn(Column.FLUX_ERR_HI);
        this.fluxErrValues = fluxErrValues;
        
        if (!isEmpty(fluxErrValues))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.FLUX_ERR_HI.getColumnInfo(), (Object) fluxErrValues));
    }

    void setFluxErrValuesLo(double[] fluxErrValuesLo) {
        removeColumn(Column.FLUX_ERR_LO);
        this.fluxErrValuesLo = fluxErrValuesLo;
        
        if (!isEmpty(fluxErrValuesLo))
            addColumn(PrimitiveArrayColumn.makePrimitiveColumn(
                Column.FLUX_ERR_LO.getColumnInfo(), (Object) fluxErrValuesLo));
    }
    
    private void removeColumn(Column column) {
        for (int i=0; i<columns.size(); i++) {
            if (column.name().equals(getColumnData(i).getColumnInfo().getName())) {
                columns.remove(i);
            }
        }
    }

    /*
     * Getters for specific column values, as of now they are only used by the 
     * unit tests, but we may want to expose this for efficiency at some point.
     * 
     */
    
    protected double[] getSpecValues() {
        return specValues;
    }

    protected double[] getFluxValues() {
        return fluxValues;
    }

    protected double[] getSpecErrValues() {
        return specErrValues;
    }

    protected double[] getSpecErrValuesLo() {
        return specErrValuesLo;
    }

    protected double[] getFluxErrValues() {
        return fluxErrValues;
    }

    protected double[] getFluxErrValuesLo() {
        return fluxErrValuesLo;
    }
    
    /**
     * Returns whether or not a double array is empty or contains all NaNs.
     * 
     */
    private static final boolean isEmpty(double[] data) {
        boolean ret = ArrayUtils.isEmpty(data);
        
        if (ret) return ret;
        
        for (int i=0; i<data.length; i++) {
            if (!Double.isNaN(data[i])) return false;
        }
        
        return true;
    }

    /**
     * Column info, descriptions, name, and identifiers for flux and spectral
     * values in an SED.
     *
     */
    public enum Column {
        SPECTRAL_COL("X axis values", "iris.spec.value"),
        SPECTRAL_ERR_HI("X axis error values", "iris.spec.value"),
        SPECTRAL_ERR_LO("X axis low error values", "iris.spec.value"),
        FLUX_COL("Y axis values", "iris.flux.value"),
        FLUX_ERR_HI("Y axis error values", "iris.flux.value"),
        FLUX_ERR_LO("Y axis low error values", "iris.flux.value");
        
        public String description;
        public String utype;
        private ColumnInfo columnInfo;
        
        private Column(String description, String utype) {
            this.description = description;
            this.utype = utype;
            this.columnInfo = new ColumnInfo(name(), Double.class, description);
            columnInfo.setUtype(utype);
        }
        
        public ColumnInfo getColumnInfo() {
            return new ColumnInfo(columnInfo);
        }
    }
}