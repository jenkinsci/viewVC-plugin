package hudson.plugins.viewVC;

import hudson.Plugin;
import hudson.scm.RepositoryBrowsers;
import hudson.model.Jobs;

/**
 * Entry point of the plugin.
 *
 * @author Mike Salnikov, based on Polarion plug-in by Jonny Wray
 * @plugin
 */
public class PluginImpl extends Plugin {

    @Override
    public void start() throws Exception {
        RepositoryBrowsers.LIST.add(ViewVCRepositoryBrowser.DESCRIPTOR);
    }

    @Override
    public void stop() throws Exception {
    }
}
