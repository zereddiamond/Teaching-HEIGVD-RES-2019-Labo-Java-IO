package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    //inspired by this website : https://stackoverflow.com/questions/2938942/file-explorer-java

    //if(rootDirectory.isDirectory()) {
      try {
          vistor.visit(rootDirectory); //visit the directory (even if it is a directory)
      } catch (IOException e) {
          e.printStackTrace();
      }
      File[] listsFilesDirectories = rootDirectory.listFiles(); //list all files if directory

      //if the array is not null
      if(listsFilesDirectories != null) {
        Arrays.sort(listsFilesDirectories); //sort the files array

        //for every file
        for (File file : listsFilesDirectories) {
          if (file.isDirectory()) { //if the file is a directory
            explore(file, vistor); //recursion
          } else { //else if it is a file
              try {
                  vistor.visit(file); //visit the file
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
        }
      }
    //}
  }
}
