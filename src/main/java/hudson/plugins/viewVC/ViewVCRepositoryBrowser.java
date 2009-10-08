package hudson.plugins.viewVC;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.scm.RepositoryBrowser;
import hudson.scm.SubversionChangeLogSet.LogEntry;
import hudson.scm.SubversionChangeLogSet.Path;
import hudson.scm.SubversionRepositoryBrowser;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * {@link SubversionRepositoryBrowser} that produces links to the ViewVC Web Client for SVN
 *
 * @author Mike Salnikov, based on Polarion plug-in by Jonny Wray
 */
public class ViewVCRepositoryBrowser extends SubversionRepositoryBrowser {

	private static final String CHANGE_SET_FORMAT = "viewvc/?view=rev&root=%s&revision=%d";
	private static final String DIFF_FORMAT = "viewvc/%s?root=%s&r1=%d&r2=%d&diff_format=h";
	private static final String FILE_FORMAT = "viewvc/%s?root=%s&view=markup";

	public final URL url;
	private final String location;

    @DataBoundConstructor
    public ViewVCRepositoryBrowser(URL url, String location) throws MalformedURLException {
		this.url = normalizeToEndWithSlash(url);
		this.location = location;
	}

    public String getLocation() {
        if(location==null)  return "/";
        return location;
    }

    @Override
    public URL getDiffLink(Path path) throws IOException {
		return new URL(url, String.format(DIFF_FORMAT, path.getValue(), getLocation(), path.getLogEntry().getRevision() - 1, path.getLogEntry().getRevision()));
    }

    @Override
    public URL getFileLink(Path path) throws IOException {
    	return new URL(url, String.format(FILE_FORMAT, path.getValue(), getLocation()));
    }

    @Override
    public URL getChangeSetLink(LogEntry changeSet) throws IOException {
    	return new URL(url, String.format(CHANGE_SET_FORMAT, getLocation(), changeSet.getRevision()));
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<RepositoryBrowser<?>> {
        public DescriptorImpl() {
            super(ViewVCRepositoryBrowser.class);
        }

        public String getDisplayName() {
            return "ViewVC";
        }

        @Override
        public ViewVCRepositoryBrowser newInstance(StaplerRequest req, JSONObject formData) throws FormException {
		   return req.bindParameters(ViewVCRepositoryBrowser.class, "viewVC.");
        }
    }
}
