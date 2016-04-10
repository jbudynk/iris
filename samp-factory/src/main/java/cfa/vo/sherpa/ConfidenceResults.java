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

package cfa.vo.sherpa;

import java.util.List;


public interface ConfidenceResults {
    Double getSigma();

    void setSigma(Double sigma);

    Double getPercent();

    void setPercent(Double percent);

    List<String> getParnames();

    void setParnames(List<String> parnames);

    Double[] getParvals();

    void setParvals(Double[] parvals);

    Double[] getParmins();

    void setParmins(Double[] parmins);

    Double[] getParmaxes();

    void setParmaxes(Double[] parmaxes);
}
