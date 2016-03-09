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

package cfa.vo.iris.visualizer.stil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cfa.vo.iris.sed.stil.SegmentStarTable;
import cfa.vo.iris.sed.stil.SerializingStarTableAdapter;
import cfa.vo.iris.sed.stil.StarTableAdapter;
import cfa.vo.iris.units.UnitsException;
import cfa.vo.iris.visualizer.preferences.VisualizerChangeEvent;
import cfa.vo.iris.visualizer.preferences.VisualizerCommand;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.common.SedInconsistentException;
import cfa.vo.sedlib.common.SedNoDataException;
import uk.ac.starlink.table.StarTable;

public class IrisStarTableAdapter {
    
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public IrisStarTable convertStarTable(Segment data) {
        try {
            SegmentStarTable segTable = new SegmentStarTable(data);
            IrisStarTable ret = new IrisStarTable(segTable);
            
            // Only serialized asynchronously for segments of length >= 3000.
            SerializingStarTableAdapter adapter = new SerializingStarTableAdapter();
            if (data.getLength() > 3000) {
                executor.submit(new AsyncSerializer(data, ret, adapter));
            } else {
                ret.setDataTable(adapter.convertStarTable(data));
            }
            
            return ret;
        } catch (SedNoDataException | SedInconsistentException | UnitsException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    private static class AsyncSerializer implements Callable<StarTable> {
        
        private final Segment data;
        private final IrisStarTable table;
        private final StarTableAdapter<Segment> adapter;

        public AsyncSerializer(Segment data, IrisStarTable table, StarTableAdapter<Segment> adapter) {
            this.data = data;
            this.table = table;
            this.adapter = adapter;
        }
        
        @Override
        public StarTable call() throws Exception {
            StarTable converted = adapter.convertStarTable(data);
            table.setDataTable(converted);
            notifyVisualizerComponents();
            return converted;
        }
        
        private void notifyVisualizerComponents() {
            VisualizerChangeEvent.getInstance().fire(null, VisualizerCommand.REDRAW);
        }
    }
}
