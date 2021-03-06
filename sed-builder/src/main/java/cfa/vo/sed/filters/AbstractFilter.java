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

package cfa.vo.sed.filters;

import java.io.File;
import java.net.URL;

/**
 *
 * @author olaurino
 */
public abstract class AbstractFilter implements IFilter {

    private URL url;

    private long lastModified;

    @Override
    public void setUrl(URL url) {
        this.url = url;
        if(url!=null && url.getProtocol().equals("file")) {
            File file = new File(url.getFile());
            lastModified = file.lastModified();
        }
    }

    @Override
    public boolean wasModified() {
        if(url!=null && url.getProtocol().equals("file")) {
            File file = new File(url.getFile());
            return lastModified != file.lastModified();
        } else {
            return false;
        }
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return getName();
    }

}
