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

package cfa.vo.iris;

import cfa.vo.interop.ISAMPController;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import cfa.vo.interop.SAMPConnectionListener;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.MessageHandler;
import org.astrogrid.samp.client.SampException;

import javax.swing.*;

/**
 *
 * This interface must implemented by enclosing application. An instance of this
 * interface is provided to the components when they are initialized, so that they can
 * access application-wide information and operations.
 *
 */
public interface IrisApplication {
    /**
     * Get a pointer to the directory that contains all the configuration files for this component.
     *
     * @return A File instance for the configuration directory.
     */
    File getConfigurationDir();
    /**
     * Return whether or not SAMP was enabled for this session. SAMP features could be required to be switched
     * on and off when the application is launched.
     *
     * @return True if SAMP is enabled in this session.
     */
    boolean isSampEnabled();

    void sendSampMessage(Message msg) throws SampException;

    ISAMPController getSAMPController();

    URL getHelpURL();

    JDialog getAboutBox();

    String getName();

    Collection<? extends IrisComponent> getComponents();

    void addMessageHandler(MessageHandler handler);

    void exitApp(int status);

    void addConnectionListener(SAMPConnectionListener sampConnectionListener);

    void addSherpaConnectionListener(SAMPConnectionListener sampConnectionListener);

    URL getDesktopIcon();

    boolean isPlatformOSX();

    void setAutoRunHub(boolean autoHub);
}
