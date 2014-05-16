/**
 * Copyright (C) 2012 Smithsonian Astrophysical Observatory
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfa.vo.iris.test.vizier;

import cfa.vo.sed.builder.SegmentImporterException;
import cfa.vo.sedlib.DoubleParam;
import cfa.vo.sedlib.Field;
import cfa.vo.sedlib.Point;
import cfa.vo.sedlib.PositionParam;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.common.SedInconsistentException;
import cfa.vo.sedlib.common.Utypes;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import uk.ac.starlink.table.StarTable;
import uk.ac.starlink.table.StoragePolicy;
import uk.ac.starlink.util.URLDataSource;
import uk.ac.starlink.votable.VOTableBuilder;

/**
 *
 * @author olaurino
 */
public class VizierImporter {

    public static final String VIZIER_DATA_DEFAULT_ENDPOINT =
            "http://cdsarc.u-strasbg.fr/viz-bin/sed?-c=:targetName&-c.rs=:searchRadius";
    
    public static final String DEFAULT_SEARCH_RADIUS = "5";
    
    public static final boolean REMOVE = false;

    public static Collection<Segment> getSedFromName(String targetName) throws SegmentImporterException {
        return getSedFromName(targetName, DEFAULT_SEARCH_RADIUS, VIZIER_DATA_DEFAULT_ENDPOINT, REMOVE);
    }

    public static Collection<Segment> getSedFromName(String targetName, String searchRadius) throws SegmentImporterException {
        return getSedFromName(targetName, searchRadius, VIZIER_DATA_DEFAULT_ENDPOINT, REMOVE);
    }
    
    public static Collection<Segment> getSedFromName(String targetName, String searchRadius, boolean remove) throws SegmentImporterException {
        return getSedFromName(targetName, searchRadius, VIZIER_DATA_DEFAULT_ENDPOINT, remove);
    }

    public static Collection<Segment> getSedFromName(String targetName, String searchRadius, String endpoint) throws SegmentImporterException {
        return getSedFromName(targetName, searchRadius, endpoint, REMOVE);
    }

    public static Collection<Segment> getSedFromName(String targetName, String searchRadius, String endpoint, boolean remove) throws SegmentImporterException {
        try {
            
            targetName = URLEncoder.encode(targetName, "UTF-8");
            searchRadius = URLEncoder.encode(searchRadius, "UTF-8");
            endpoint = endpoint.replace(":targetName&-c.rs=:searchRadius", targetName+"&-c.rs="+searchRadius);
            URL nedUrl = new URL(endpoint);

            VOTableBuilder vob = new VOTableBuilder();
            StarTable table = vob.makeStarTable(new URLDataSource(nedUrl), true, StoragePolicy.ADAPTIVE);

            Map<String, Segment> segMap = new HashMap();
            
            Set<String> pointSet = new HashSet<String>();
            
            if (remove == true) {
                for (long i = 0; i < table.getRowCount(); i++) {
                    Double ra = (Double) table.getCell(i, 0);
                    Double dec = (Double) table.getCell(i, 1);
                    //String coordString = ra.toString() + dec.toString();
                    
                    Double spectral = (Double) table.getCell(i, 4);
                    Double flux = ((Float) table.getCell(i, 5)).doubleValue();
                    Double err = ((Float) table.getCell(i, 6)).doubleValue();
                    
                    String spectralString = String.format("%.4g", spectral);
                    String fluxString = String.format("%.4g", flux);
                    String errString = String.format("%.4g", err);
                    
                    String points = spectralString+fluxString;
                    
                    String coordString = points;
                                        
                    if (!pointSet.contains(points)) {

                        if (!segMap.containsKey(coordString)) {
                            Segment s = new Segment();
                            segMap.put(coordString, s);
                            s.createData().createPoint();
                            s.createChar().createSpectralAxis().setUnit("GHz");
                            s.createChar().createSpectralAxis().setUcd("em.freq");
                            s.createChar().createFluxAxis().setUnit("Jy");
                            s.createChar().createFluxAxis().setUcd("phot.flux.density;em.freq");
                            s.createChar().createFluxAxis().createAccuracy().createStatError().setUnit("Jy");
                            s.createChar().createFluxAxis().createAccuracy().createStatError().setUcd("stat.error;phot.flux.density");
                            s.createTarget().createName().setValue(targetName);
                            PositionParam pp = new PositionParam();
                            pp.setValue(new DoubleParam[]{new DoubleParam(ra), new DoubleParam(dec)});
                            s.createTarget().setPos(pp);
                            s.createCuration().createPublisher().setValue("Vizier - CDS");
                        }

                        pointSet.add(points);
                        System.out.println("Added a point. "+points);

                        Segment s = segMap.get(coordString);
                        Point p = new Point();
                        p.createSpectralAxis().setValue(new DoubleParam(spectral));
                        p.createFluxAxis().setValue(new DoubleParam(flux));
                        p.createFluxAxis().createAccuracy().setStatError(new DoubleParam(err));
                        s.createData().createPoint().add(p);
                        Field xf = new Field();
                        xf.setUcd("em.freq");
                        xf.setUnit("GHz");
                        s.createData().setDataInfo(xf, "Spectrum.Data.SpectralAxis.Value");
                        Field yf = new Field();
                        yf.setUcd("phot.flux.density;em.freq");
                        yf.setUnit("Jy");
                        s.createData().setDataInfo(yf, "Spectrum.Data.FluxAxis.Value");
                        Field ef = new Field();
                        ef.setUcd("stat.error;phot.flux.density");
                        ef.setUnit("Jy");
                        s.createData().setDataInfo(ef, "Spectrum.Data.FluxAxis.Accuracy.StatError");

                    } else {
                        if (!Double.isNaN(err) && err != 0.0) {
                            Segment s = segMap.get(coordString);
                            double[] lerr = new double[1];
                            lerr[0] = err;
                            double[] oldErr = null;
                            try {
                                oldErr = (double[])s.getDataValues(Utypes.SEG_DATA_FLUXAXIS_ACC_STATERR);
                                System.out.println("OldErr: "+oldErr[0]);
                            } catch (SedInconsistentException ex) {
                                System.err.println(ex.toString());
                            }
                            if (Double.isNaN(oldErr[0]) || oldErr[0] == 0.0) {
                                s.setDataValues(lerr, Utypes.SEG_DATA_FLUXAXIS_ACC_STATERR);
                                System.out.println("New error: "+err);
                            }
                        }
                    }
                }
            } else {
                for (long i = 0; i < table.getRowCount(); i++) {
                    Double ra = (Double) table.getCell(i, 0);
                    Double dec = (Double) table.getCell(i, 1);
                    //String coordString = ra.toString() + dec.toString();
                    String coordString = (String) table.getCell(i, 2);
                    
                    if (!segMap.containsKey(coordString)) {
                        Segment s = new Segment();
                        segMap.put(coordString, s);
                        s.createData().createPoint();
                        s.createChar().createSpectralAxis().setUnit("GHz");
                        s.createChar().createSpectralAxis().setUcd("em.freq");
                        s.createChar().createFluxAxis().setUnit("Jy");
                        s.createChar().createFluxAxis().setUcd("phot.flux.density;em.freq");
                        s.createChar().createFluxAxis().createAccuracy().createStatError().setUnit("Jy");
                        s.createChar().createFluxAxis().createAccuracy().createStatError().setUcd("stat.error;phot.flux.density");
                        s.createTarget().createName().setValue(targetName);
                        PositionParam pp = new PositionParam();
                        pp.setValue(new DoubleParam[]{new DoubleParam(ra), new DoubleParam(dec)});
                        s.createTarget().setPos(pp);
                        s.createCuration().createPublisher().setValue("Vizier - CDS");
                    }

                    Double spectral = (Double) table.getCell(i, 4);
                    Double flux = ((Float) table.getCell(i, 5)).doubleValue();
                    Double err = ((Float) table.getCell(i, 6)).doubleValue();

                    Segment s = segMap.get(coordString);
                    Point p = new Point();
                    p.createSpectralAxis().setValue(new DoubleParam(spectral));
                    p.createFluxAxis().setValue(new DoubleParam(flux));
                    p.createFluxAxis().createAccuracy().setStatError(new DoubleParam(err));
                    s.createData().createPoint().add(p);
                    Field xf = new Field();
                    xf.setUcd("em.freq");
                    xf.setUnit("GHz");
                    s.createData().setDataInfo(xf, "Spectrum.Data.SpectralAxis.Value");
                    Field yf = new Field();
                    yf.setUcd("phot.flux.density;em.freq");
                    yf.setUnit("Jy");
                    s.createData().setDataInfo(yf, "Spectrum.Data.FluxAxis.Value");
                    Field ef = new Field();
                    ef.setUcd("stat.error;phot.flux.density");
                    ef.setUnit("Jy");
                    s.createData().setDataInfo(ef, "Spectrum.Data.FluxAxis.Accuracy.StatError");
                }
            }
            return segMap.values();
        }
         catch (Exception ex) {
            throw new SegmentImporterException(ex);
         }
    }
}
        

/*            for (long i = 0; i < table.getRowCount(); i++) {
                Double ra = (Double) table.getCell(i, 0);
                Double dec = (Double) table.getCell(i, 1);
                //String coordString = ra.toString() + dec.toString();
                String coordString = String.valueOf(i);
                
                if (!segMap.containsKey(coordString)) {
                    Segment s = new Segment();
                    segMap.put(coordString, s);
                    s.createData().createPoint();
                    s.createChar().createSpectralAxis().setUnit("GHz");
                    s.createChar().createSpectralAxis().setUcd("em.freq");
                    s.createChar().createFluxAxis().setUnit("Jy");
                    s.createChar().createFluxAxis().setUcd("phot.flux.density;em.freq");
                    s.createChar().createFluxAxis().createAccuracy().createStatError().setUnit("Jy");
                    s.createChar().createFluxAxis().createAccuracy().createStatError().setUcd("stat.error;phot.flux.density");
                    s.createTarget().createName().setValue(targetName);
                    PositionParam pp = new PositionParam();
                    pp.setValue(new DoubleParam[]{new DoubleParam(ra), new DoubleParam(dec)});
                    s.createTarget().setPos(pp);
                    s.createCuration().createPublisher().setValue("Vizier - CDS");
                }
                
                Double spectral = (Double) table.getCell(i, 4);
                Double flux = ((Float) table.getCell(i, 5)).doubleValue();
                Double err = ((Float) table.getCell(i, 6)).doubleValue();
                
                if (remove == true) {
                    String points = String.valueOf(spectral)+String.valueOf(flux)+String.valueOf(err);
                    if (!pointSet.contains(points)) {
                        pointSet.add(points);
                        System.out.println("Added a point.");
                        
                        Segment s = segMap.get(coordString);
                        Point p = new Point();
                        p.createSpectralAxis().setValue(new DoubleParam(spectral));
                        p.createFluxAxis().setValue(new DoubleParam(flux));
                        p.createFluxAxis().createAccuracy().setStatError(new DoubleParam(err));
                        s.createData().createPoint().add(p);
                        Field xf = new Field();
                        xf.setUcd("em.freq");
                        xf.setUnit("GHz");
                        s.createData().setDataInfo(xf, "Spectrum.Data.SpectralAxis.Value");
                        Field yf = new Field();
                        yf.setUcd("phot.flux.density;em.freq");
                        yf.setUnit("Jy");
                        s.createData().setDataInfo(yf, "Spectrum.Data.FluxAxis.Value");
                        Field ef = new Field();
                        ef.setUcd("stat.error;phot.flux.density");
                        ef.setUnit("Jy");
                        s.createData().setDataInfo(ef, "Spectrum.Data.FluxAxis.Accuracy.StatError");

                    } else {
                        System.out.println("Found Duplicates");
                        System.out.println(points); 
                    } 
                 


                } else {
                    Segment s = segMap.get(coordString);
                    Point p = new Point();
                    p.createSpectralAxis().setValue(new DoubleParam(spectral));
                    p.createFluxAxis().setValue(new DoubleParam(flux));
                    p.createFluxAxis().createAccuracy().setStatError(new DoubleParam(err));
                    s.createData().createPoint().add(p);
                    Field xf = new Field();
                    xf.setUcd("em.freq");
                    xf.setUnit("GHz");
                    s.createData().setDataInfo(xf, "Spectrum.Data.SpectralAxis.Value");
                    Field yf = new Field();
                    yf.setUcd("phot.flux.density;em.freq");
                    yf.setUnit("Jy");
                    s.createData().setDataInfo(yf, "Spectrum.Data.FluxAxis.Value");
                    Field ef = new Field();
                    ef.setUcd("stat.error;phot.flux.density");
                    ef.setUnit("Jy");
                    s.createData().setDataInfo(ef, "Spectrum.Data.FluxAxis.Accuracy.StatError");
                }
            }
            return segMap.values();
        }   
         catch (Exception ex) {
            throw new SegmentImporterException(ex);
         }
    }
}
 * 
 */
            
    /*
    boolean duplicates(final int[] zipcodelist) {
        Set<Integer> lump = new HashSet<Integer>();
        for (int i : zipcodelist)
        {
            if (lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
    }
     *
     */
    
    /* Check for duplicate points.
     * 
     * If duplicate point values are in segMap, fire VizierDuplicatePointsFrame
     * and based on the user input, keep or remove all duplicate points.
     * 
     * Check for duplicate points
     * 
     * TODO
     */
    /*
    Iterator iter = segMap.entrySet().iterator();
    while (iter.hasNext()) {
        segMap.values().getDataValues("Spectrum.Data.SpectralAxis.Value");
    }
    Integer keySize = segMap.values().getDataValues().size();
    Set valueSetSize = new HashSet(segMap.values());
    if (keySize != valueSetSize.size()) {
        System.out.println("\n Not the same size! \n");
        System.out.println("segMap size = " + keySize);
        System.out.println("valueSetSize = " + valueSetSize.size() + "\n");
    }

    /*.getDataValues("Spectrum.Data.FluxAxis.Value");*/
    /*Set<Segment> pointsSet = new HashSet<Segment>(Arrays.asList(segMap.values().getPoint()));*/



    /* If keep, return segMap as-is
    return segMap.values();

    /* If remove duplicates, remove the duplicate points. Points that  
     * have associated errors will be kept over points with no
     * uncertainties.
     * 
     * TODO
     */
    
    /*public void getSedFromSAMP(Mtype sampMessage, String sampUrl) {*/
        
//    public static Sed getError() throws SegmentImporterException {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(NEDImporter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        throw new SegmentImporterException(new ConnectException());
//    }
