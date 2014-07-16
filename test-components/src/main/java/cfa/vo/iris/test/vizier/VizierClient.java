/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.iris.test.vizier;

import cfa.vo.iris.AbstractDesktopItem;
import cfa.vo.iris.ICommandLineInterface;
import cfa.vo.iris.IMenuItem;
import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.NullCommandLineInterface;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.sed.builder.SedBuilder;
import cfa.vo.sed.gui.SampChooser;
import cfa.vo.sedlib.Sed;
import cfa.vo.sedlib.Segment;
import cfa.vo.sedlib.io.SedFormat;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.AbstractMessageHandler;
import org.astrogrid.samp.client.HubConnection;
import org.astrogrid.samp.client.MessageHandler;

/**
 *
 * @author olaurino
 */
public class VizierClient implements IrisComponent {

    private IrisApplication app;
    private IWorkspace workspace;
	private static IWorkspace workspce;
	private static IrisApplication iris;
	private static SedlibSedManager sedManager;

    @Override
    public void init(IrisApplication app, IWorkspace workspace) {
        this.app = app;
        this.workspace = workspace;
    }

    @Override
    public String getName() {
        return "VizierClient";
    }

    @Override
    public String getDescription() {
        return "Vizier SED Client";
    }

    @Override
    public ICommandLineInterface getCli() {
        return new NullCommandLineInterface("vizier");
    }

    @Override
    public void initCli(IrisApplication app) {
        
    }

    @Override
    public List<IMenuItem> getMenus() {
        return new MenuItems();
    }

    @Override
    public List<MessageHandler> getSampHandlers() {
		List<MessageHandler> list = new ArrayList();
        list.add(new VizierSAMPHandler());

        return list;
    }

    @Override
    public void shutdown() {
        
    }

    private class MenuItems extends ArrayList<IMenuItem>{
        private JInternalFrame frame;

        public MenuItems() {
            add(new AbstractDesktopItem("Vizier SED Client", "Retrieve Photometry Data from Vizier", "/vizier.jpeg", "/tool_tiny.png") {

                @Override
                public void onClick() {
                    if(frame==null) {
                        frame = new VizierFrame(workspace.getSedManager());
                        workspace.addFrame(frame);
                    }


                    frame.setVisible(true);
                }
            });
        }
    }
	
	private static class VizierSAMPHandler extends AbstractMessageHandler {

        public VizierSAMPHandler() {
            super(new String[]{"table.load.votable",
                        "table.load.fits",});
        }

        @Override
        public Map processCall(HubConnection hc, String senderId, Message msg) throws MalformedURLException {
            senderId = iris.getSAMPController().getClientMap().get(senderId).toString();
            String formatName = msg.getMType().toLowerCase().equals("table.load.votable") ? "VOT" : "FITS";
            String tableName = (String) msg.getParam("name");
            if (tableName == null || tableName.isEmpty()) {
                tableName = (String) msg.getParam("table-id");
            }
			ExtSed sed = sedManager.getSelected() != null ? sedManager.getSelected() : sedManager.newSed("Vizier");
			/**
			try {
				Collection<Segment> segments = VizierImporter.getSedFromSAMP(msg);
				List<Segment> segs = new ArrayList(segments);
				sed.addSegment(segs);
			} catch (Exception ex) {
				URL url = new URL((String) msg.getParam("url"));
				Logger.getLogger(SedBuilder.class.getName()).log(Level.SEVERE, null, ex);
				return doImport(tableName, senderId, url, formatName, sed);
			}
			**/
			try {
				Collection<Segment> segments = VizierImporter.getSedFromSAMP(msg);
				List<Segment> segs = new ArrayList(segments);
				sed.addSegment(segs);
			} catch (Exception ex) {
				Logger.getLogger(SedBuilder.class.getName()).log(Level.SEVERE, "shithappened and it aint good");
			}
			
			return null;
		}
		
		 private Map doImport(String tableId, String senderId, URL url, String formatName, ExtSed sed) {
            SampChooser chooser = new SampChooser(tableId, senderId, url, formatName, sed, workspce);
            workspce.addFrame(chooser);
            chooser.setVisible(true);
            return null;
        }
	}

}
