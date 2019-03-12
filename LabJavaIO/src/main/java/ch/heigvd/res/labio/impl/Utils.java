package ch.heigvd.res.labio.impl;

import ch.heigvd.res.labio.impl.filters.FileNumberingFilterWriter;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments.
   *
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    String[] arrayLines = new String[2];

    if(!lines.contains("\n") && !lines.contains("\r")) {
      arrayLines[0] = "";
      arrayLines[1] = lines;

      return arrayLines;
    }

    boolean endOfLine = false;
    boolean firstLineAdded = false;

    //This website helped me : https://stackoverflow.com/questions/30587956/how-to-split-string-before-first-comma/30588106
    for (int i = 0; i < lines.length(); ++i) {
      if (lines.charAt(i) == '\n') {

        arrayLines[0] = lines.substring(0, lines.indexOf('\n') + 1);
        arrayLines[1] = lines.substring(lines.indexOf('\n') + 1, lines.length());

        return arrayLines;
      }

      if (lines.charAt(i) == '\r') {
        endOfLine = true;
      }

      if ((lines.charAt(i) != '\r' && lines.charAt(i) != '\n')) {
        if (endOfLine) {
          arrayLines[0] = lines.substring(0, lines.indexOf('\r') + 1);
          arrayLines[1] = lines.substring(lines.indexOf('\r') + 1, lines.length());

          return arrayLines;
        }
      }
    }

    if(endOfLine) {
      arrayLines[0] = lines.substring(0, lines.indexOf('\r') + 1);
      arrayLines[1] = lines.substring(lines.indexOf('\r') + 1, lines.length());

      return arrayLines;
    }

    return arrayLines;
  }
}
