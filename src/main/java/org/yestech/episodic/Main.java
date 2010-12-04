package org.yestech.episodic;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.yestech.episodic.objectmodel.Episode;
import org.yestech.episodic.objectmodel.Episodes;
import org.yestech.episodic.objectmodel.Shows;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.err;
import static java.lang.System.out;

public class Main {

    public static enum Operation {
        list_shows,
        list_episodes,
        create_episode,
        create_asset,
        full_create
    }

    @Option(required = true, name = "-o", aliases = "--operation", usage = "Operation to run")
    private Operation operation;

    @Option(required = true, name = "-api", aliases = "--api-key", usage = "Sets the api key")
    private String apiKey;

    @Option(required = true, name = "-s", aliases = "--secret", usage = "Sets the episodic secret key")
    private String secret;

    @Option(name = "-page", usage = "Set the number of pages to return in the result")
    private Integer page;

    @Option(name = "-per-page", usage = "Number of results per page")
    private Integer perPage;

    @Option(name = "-show", usage = "Sets a show id [list_episodes ,list_shows]", multiValued = true)
    private List<String> showIds = new ArrayList<String>();

    @Option(name = "-sort", usage = "How to sort the results (update_at, created_at, name) [list_episodes,list_shows]")
    private EpisodicService.SortBy sortBy;

    @Option(name = "-sort-dir", usage = "Direction to sort by (asc, dsc) [list_episodes,list_shows]")
    private EpisodicService.SortDir sortDir;

    @Option(name = "-episode", usage = "List of episode ids [list_episodes]")
    private List<String> episodeIds = new ArrayList<String>();

    @Option(name = "-search-term", usage = "Search term [list_episode]")
    private String searchTerm;

    @Option(name = "-tag-mode", usage = "Tag Mode (any, all) [list_episode]")
    private EpisodicService.TagMode tagMode;

    @Option(name = "-episode-status", usage = "Status of episode (off_the_air,publishing,on_the_air,waiting_to_air,publish_failed) [list_episodes]")
    private Episode.EpisodeStatus episodeStatus;

    @Option(name = "-include-views", usage = "Whether or not to include the total number of views (true, false) [list_episodes]")
    private Boolean includeViews;

    @Option(name = "-embed-width", usage = "An integer value in pixels that specifies the width of the video [list_episode]")
    private Integer embedWidth;

    @Option(name = "-embed-height", usage = "An integer value in pixels that specifies the height of the video [list_episode]")
    private Integer embedHeight;

    @Option(name = "-search-type", usage = "The search_type parameter must be one of \"tags\", \"name_description\" or \"all\". The default is \"all\". [list_episode]")
    private EpisodicService.SearchType searchType;

    @Option(name = "-name", usage = "Name of the thing object to be created [create_episode (required), create_asset (required)]")
    private String name;

    @Option(name = "-file", usage = "File to upload as an asset [create_asset (required)]")
    private File file;

    @Option(name = "-tag", multiValued = true, usage = "Tag to applied to the asset or episode [create_episode, create_asset]")
    private List<String> tags = new ArrayList<String>();

    @Option(name = "-publish", usage = "Whether a created episode should be published, default false [create_episode]")
    private boolean publish = false;

    @Option(name = "-desc", usage = "Description of a created episode [create_episode]")
    private String description;

    @Option(name = "-asset", usage = "id of an asset [create_episode (required)]")
    private List<String> assetIds;

    @Option(name = "-pingUrl", usage = "Url for the application to ping back after publish [create_episode]")
    private String pingUrl;

    @Option(name = "-proxy-port", usage = "Port for http proxy")
    private Integer proxyPort;

    @Option(name = "-proxy-host", usage = "Host for proxy")
    private String proxyHost;

    String[] shows() {
        return showIds.toArray(new String[showIds.size()]);
    }

    String[] episodes() {
        return episodeIds.toArray(new String[episodeIds.size()]);
    }

    String[] tags() {
        return tags.toArray(new String[tags.size()]);
    }

    String[] assets() {
        return assetIds.toArray(new String[assetIds.size()]);
    }

    public static void main(String[] args) {

        // parse the command line arguments and options
        Main main = new Main();
        CmdLineParser parser = new CmdLineParser(main);
        parser.setUsageWidth(80); // width of the error display area
        try {
            parser.parseArgument(args);

            DefaultEpisodicService service;
            if (main.proxyHost != null && main.proxyPort != null) {
                service = new DefaultEpisodicService(main.secret, main.apiKey, main.proxyHost, main.proxyPort);
            }
            else {
                service = new DefaultEpisodicService(main.secret, main.apiKey);
            }

            try {
                out.println("Executing: " + main.toString());
                Object xmlResult = null;
                if (main.operation == Operation.list_shows) {
                    xmlResult = list_shows(service, main);
                } else if (main.operation == Operation.list_episodes) {
                    xmlResult = list_episodes(service, main);
                } else if (main.operation == Operation.create_asset) {
                    String assetId = create_asset(service, main);
                    System.out.println("Successfully created asset with id " + assetId);
                } else if (main.operation == Operation.create_episode) {
                    String episodeId = create_episode(service, main);
                    System.out.println("Successfully created episode with id "+episodeId);
                } else if (main.operation == Operation.full_create) {
                    String episodeId = full_create(service, main);
                    System.out.println("Successfully created episode with id "+episodeId);
                }

                if (xmlResult != null) {
                    displayXmlResult(xmlResult);
                }
            } finally {
                service.destroy();
            }

        } catch (CmdLineException e) {
            err.println(e.getMessage());
            err.println("java Main [options...] arguments...");
            // print the list of available options
            parser.printUsage(err);
            err.println();
            System.exit(1);
        } catch (EpisodicException e) {
            err.print(e.getMessage() + " Code: " + e.getCode());
            e.printStackTrace();
            System.exit(1);
        } catch (JAXBException e) {
            err.print(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static Shows list_shows(EpisodicService service, Main main) throws JAXBException {
        return service.getShows(main.shows(), main.sortBy, main.sortDir, main.page, main.perPage);
    }

    private static Episodes list_episodes(EpisodicService service, Main main) {
        return service.getEpisodes(main.shows(), main.episodes(), main.searchTerm, main.searchType,
                main.tagMode, main.episodeStatus, main.sortBy, main.sortDir, main.includeViews, main.page, main.perPage,
                main.embedWidth, main.embedHeight);
    }

    private static String create_asset(EpisodicService service, Main main) throws CmdLineException {
        if (main.name == null) {
            throw new CmdLineException("name is required for create_asset");
        }
        if (main.showIds == null || main.showIds.isEmpty() || main.showIds.size() > 1) {
            throw new CmdLineException("1 and only show is required for create_asset");
        }
        if (main.file == null) {
            throw new CmdLineException("file is required");
        }
        return service.createAsset(main.showIds.get(0), main.name, main.file, main.tags());
    }

    private static String create_episode(EpisodicService service, Main main) throws CmdLineException {
        if (main.name == null) {
            throw new CmdLineException("name is required for create_episode");
        }
        if (main.showIds == null || main.showIds.isEmpty() || main.showIds.size() > 1) {
            throw new CmdLineException("1 and only show is required for create_episode");
        }

        return service.createEpisode(main.showIds.get(0), main.name, main.assets(), main.publish, main.description,
                main.pingUrl, main.tags());

    }

    private static String full_create(EpisodicService service, Main main) throws CmdLineException {
         if (main.name == null) {
            throw new CmdLineException("name is required for full_create");
        }
        if (main.showIds == null || main.showIds.isEmpty() || main.showIds.size() > 1) {
            throw new CmdLineException("1 and only show is required for full_create");
        }
        if (main.file == null) {
            throw new CmdLineException("file is required");
        }
        if (main.file == null) {
            throw new CmdLineException("file is required");
        }
        String assetId = service.createAsset(main.showIds.get(0), main.name, main.file, main.tags());
        System.out.println("Created asset --> "+assetId);

        return service.createEpisode(main.showIds.get(0), main.name, new String[] {assetId}, main.publish, main.description,
                main.pingUrl, main.tags());
    }


    public static void displayXmlResult(Object result) throws JAXBException {
        out.println("XML Response:");
        JAXBContext context = JAXBContext.newInstance(result.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(result, out);
    }

    @Override
    public String toString() {
        return "Main{" +
                "operation=" + operation +
                ", apiKey='" + apiKey + '\'' +
                ", secret='" + secret + '\'' +
                ", page=" + page +
                ", perPage=" + perPage +
                ", showIds=" + showIds +
                ", sortBy=" + sortBy +
                ", sortDir=" + sortDir +
                ", episodeIds=" + episodeIds +
                ", searchTerm='" + searchTerm + '\'' +
                ", tagMode=" + tagMode +
                ", episodeStatus=" + episodeStatus +
                ", includeViews=" + includeViews +
                ", embedWidth=" + embedWidth +
                ", embedHeight=" + embedHeight +
                ", searchType=" + searchType +
                '}';
    }
}
