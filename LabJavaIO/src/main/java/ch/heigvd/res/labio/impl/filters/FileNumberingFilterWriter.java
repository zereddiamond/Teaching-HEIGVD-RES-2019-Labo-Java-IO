package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  private int lineNumber = 1; //line number begins at 1

  private boolean firstLineAdded = false; //to know if the first line added

  private boolean endOfLine = false; //to check if is it end of line

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }


  //inspired by this website : https://stackoverflow.com/questions/17016242/reading-writing-files-in-java-and-adding-line-numbers
  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //inspired by this : https://stackoverflow.com/questions/19223166/is-there-a-substringstart-length-function
    if(off > 0) {
      str = str.substring(off, Math.min(str.length(), len + off));
    }

    //for every character in string
    for(int i = 0; i < str.length(); ++i) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");
    //super.write(cbuf, off, len);

    //for every character in array
    for(int i = 0; i < cbuf.length; ++i) {
      super.write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //if the first line is not added
    if(!firstLineAdded) {
      lineNumber = writeLineNumbers(lineNumber);

      super.write(c);

      firstLineAdded = true;
      return;
    }

    //if character is LF
    if(c == '\n') {
      if(endOfLine) { //on Windows (\r\n)
        super.write('\r');
      }

      super.write(c);

      lineNumber = writeLineNumbers(lineNumber);
      endOfLine = false;

      return;
    }

    //if character is a CR
    //do not write immedately
    if(c == '\r') {
      endOfLine = true;
    }

    //if it is an another character
    if(c != '\r' && c != '\n') {
      if(endOfLine) {
        super.write('\r');

        lineNumber = writeLineNumbers(lineNumber);

        endOfLine = false;
      }
      super.write(c);
    }
  }

  //function for write the number line
  private int writeLineNumbers(int lineNumber) throws IOException {
    write(String.valueOf(lineNumber).toCharArray());

    super.write('\t');
    ++lineNumber;

    return lineNumber;
  }

}

