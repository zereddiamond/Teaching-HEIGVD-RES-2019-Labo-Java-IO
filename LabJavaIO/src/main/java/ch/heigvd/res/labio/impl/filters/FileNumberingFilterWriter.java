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

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }


  //inspired by this website : https://stackoverflow.com/questions/17016242/reading-writing-files-in-java-and-adding-line-numbers
  @Override
  public void write(String str, int off, int len) throws IOException {
    //throw new UnsupportedOperationException("The student has not implemented this method yet.");

    Integer lineNumber = new Integer(1);
    /*StringBuilder sb = new StringBuilder();

    sb.append(lineNumber.toString() + "\t");
    sb.append(str);*/
    //String newString = lineNumber.toString() + "\t" + str;
    StringBuffer sb = new StringBuffer();

    sb.append(lineNumber.toString());
    sb.append("\t");
    sb.append(str);
    ++lineNumber;
    sb.append(lineNumber.toString());
    sb.append("\t");
    len += 4;


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
    super.write(c);
  }

}
