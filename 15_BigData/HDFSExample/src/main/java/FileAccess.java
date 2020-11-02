import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class FileAccess
{
    private FileSystem hdfs;

    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     * for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) throws Exception
    {
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");

        hdfs = FileSystem.get(new URI(rootPath), configuration);

    }

    /**
     * Creates empty file or directory
     *
     * @param pathName
     */
    public void create(String pathName)
    {
        try {
            Path path = new Path(pathName);
            if(hdfs.exists(path)) {
                System.out.println("This file or directory has already exist.");
                return;
            }

            if(pathName.contains(".")){
                hdfs.createNewFile(path);
                System.out.println("Created new file.");
            }
            else {
                hdfs.mkdirs(path);
                System.out.println("Created new directory.");
            }

        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }
    }

    /**
     * Appends content to the file
     *
     * @param pathName
     * @param content
     */
    public void append(String pathName, String content)
    {
        try {
            Path path = new Path(pathName);

            if(!hdfs.exists(path) && hdfs.isFile(path)) {
                System.out.println("Path does not exist or it is not a file!");
            }
            else {
                FSDataOutputStream outputStream = hdfs.append(path);
                PrintWriter writer = new PrintWriter(outputStream);

                writer.append(content);

                writer.flush();
                outputStream.flush();
                writer.close();
                outputStream.close();

                System.out.println("New content added.");
            }
        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }
    }

    /**
     * Returns content of the file
     *
     * @param pathName
     * @return
     */
    public String read(String pathName)
    {
        StringBuilder output = new StringBuilder();

        try {
            Path path = new Path(pathName);

            if(!hdfs.exists(path) && hdfs.isFile(path)) {
                System.out.println("Path does not exist or it is not a file!");
            }
            else {
                FSDataInputStream inputStream = hdfs.open(path);
                output.append(IOUtils.toString(inputStream, "UTF-8"));
                inputStream.close();
            }
        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }

        return output.toString();
    }

    /**
     * Deletes file or directory
     *
     * @param pathName
     */
    public void delete(String pathName)
    {
        try {
            Path path = new Path(pathName);

            if(!hdfs.exists(path))
                System.out.println("Path does not exist!");
            else {
                hdfs.delete(path, true);
                System.out.println("Deleted.");
            }

        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param pathName
     * @return
     */
    public boolean isDirectory(String pathName)
    {
        boolean isDirectory = false;

        try {
            Path path = new Path(pathName);
            if(hdfs.isDirectory(path))
                isDirectory = true;

        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }

        return isDirectory;
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param pathName
     * @return
     */
    public List<String> list(String pathName)
    {
        List<String> list = new LinkedList<>();

        try {
            Path path = new Path(pathName);

            if(hdfs.isFile(path)) {
                System.out.println("This is file, not a directory.");
            }
            else {
                FileStatus[] fileStatus = hdfs.listStatus(path);
                for (FileStatus fileStat : fileStatus) {
                    if (fileStat.isDirectory()) {
                        list.add(fileStat.getPath().toString());
                        list.addAll(list(fileStat.getPath().toString()));
                    } else {
                        list.add(fileStat.getPath().toString());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Wrong path!");
            e.printStackTrace();
        }

        return list;
    }

    public void close() throws IOException
    {
        hdfs.close();
    }
}