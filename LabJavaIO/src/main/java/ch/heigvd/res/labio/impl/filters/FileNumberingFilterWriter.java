package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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

  private int lineNumber = 1;

  private boolean newLineNumberIsAdded = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }


  //inspired by this website : https://stackoverflow.com/questions/17016242/reading-writing-files-in-java-and-adding-line-numbers
  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    //Integer lineNumber = new Integer(1);
    /*StringBuilder sb = new StringBuilder();

    sb.append(lineNumber.toString() + "\t");
    sb.append(str);*/
    //String newString = lineNumber.toString() + "\t" + str;

    StringBuffer sb = new StringBuffer();

    //inspired by this : https://stackoverflow.com/questions/19223166/is-there-a-substringstart-length-function
    if(off > 0) {
      str = str.substring(off);
      off = 0;
    }

    if(newLineNumberIsAdded) {
      sb.append(str);
      newLineNumberIsAdded = false;
    } else {
      sb.append(lineNumber);
      sb.append("\t");
      len += 2;
      sb.append(str);
      newLineNumberIsAdded = true;
    }

    if(str.contains("\n")) {
      ++lineNumber;
      sb.append(lineNumber);
      sb.append("\t");
      len += 2;
      newLineNumberIsAdded = true;
    }

    String newString = sb.toString();

    super.write(newString, off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");



    super.write(cbuf, off, len);
  }

  @Override
  public void write(int c) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    if(newLineNumberIsAdded) {
      if(c == '\n') {
        super.write(c);
        super.write(lineNumber + '0');
        super.write('\t');

        ++lineNumber;
      } else {
        super.write(c);
      }
    } else {
      super.write(lineNumber + '0');
      super.write('\t');
      super.write(c);

      ++lineNumber;

      newLineNumberIsAdded = true;
    }
  }
}
