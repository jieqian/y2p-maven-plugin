package me.cloud.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import me.cloud.plugin.utils.Constants;
import me.cloud.plugin.utils.Env;

import java.io.*;
import java.util.Properties;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "run", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.basedir}/tmp", property = "targetDir" )
    private File outputDirectory;

    @Parameter( defaultValue = "${project.basedir}", property = "basedir")
    private String basedir;

    @Parameter(defaultValue = "basic", property = "env" )
    private String env;

    @Parameter(defaultValue = "false", property = "formatKey")
    private boolean formatKey;

    private static final String APPENDIX = ".properties";
    public void execute()
        throws MojoExecutionException
    {
        File f = outputDirectory;

        if ( !f.exists() )
        {
            f.mkdirs();
        }

        File properties;
        if("basic".equals(env)){
            properties = new File( f, Constants.APPLICATION + APPENDIX );
        }else {
            properties = new File( f, Constants.APPLICATION + "-" + env + APPENDIX );
        }

        OutputStream output = null;
        DefaultYamlProcessor processor = new DefaultYamlProcessor(basedir,Env.valueOf(env),formatKey);
        try
        {
            output = new FileOutputStream( properties );
            Properties prop = processor.createProperties();
            prop.store(output, null);
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error creating file " + properties, e );
        }
        finally
        {
            if ( output != null )
            {
                try
                {
                    output.close();
                }
                catch ( IOException e )
                {
                    // ignore
                }
            }
        }
    }
}
