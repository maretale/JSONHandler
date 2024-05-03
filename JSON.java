import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;

/**
 * Utilities for our simple implementation of JSON.
 * @author Connor Heagy
 * @author Alex Maret
 */
public class JSON {
  // +---------------+-----------------------------------------------
  // | Static fields |
  // +---------------+

  /**
   * The current position in the input.
   */
  static int pos;

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Parse a string into JSON.
   */
  public static JSONValue parse(String source) throws ParseException, IOException {
    return parse(new StringReader(source));
  } // parse(String)

  /**
   * Parse a file into JSON.
   */
  public static JSONValue parseFile(String filename) throws ParseException, IOException {
    FileReader reader = new FileReader(filename);
    JSONValue result = parse(reader);
    reader.close();
    return result;
  } // parseFile(String)

  /**
   * Parse JSON from a reader.
   */
  public static JSONValue parse(Reader source) throws ParseException, IOException {
    pos = 0;
    JSONValue result = parseKernel(source);
    if (-1 != skipWhitespace(source)) {
      throw new ParseException("Characters remain at end", pos);
    }
    return result;
  } // parse(Reader)

  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  /**
   * Parse JSON from a reader, keeping track of the current position
   */
  static JSONValue parseKernel(Reader source) throws ParseException, IOException {
    int ch;
    ch = skipWhitespace(source);
    if (-1 == ch) {
      throw new ParseException("Unexpected end of file", pos);
    } // if
    if (ch == 't') {
      incrementReader(source, 3);
      return JSONConstant.TRUE;
    } // if
    if (ch == 'f') {
      incrementReader(source, 4);
      return JSONConstant.FALSE;
    } // if
    if (ch == 'n') {
      incrementReader(source, 3);
      return JSONConstant.NULL;
    } // if 
    if (ch == '"'){
      return parseString(source);
    } // if
    if (ch == '[') {
      return parseArray(source);
    } // if
    if (ch == '{') {
      return parseHash(source);
    } // if
    return parseNumber(source, ch);
  } // parseKernel

  /**
   * Get the next character from source, skipping over whitespace.
   */
  static int skipWhitespace(Reader source) throws IOException {
    int ch;
    do {
      ch = source.read();
      ++pos;
    } while (isWhitespace(ch));
    return ch;
  } // skipWhitespace(Reader)

  /**
   * Determine if a character is JSON whitespace (newline, carriage return,
   * space, or tab).
   */
  static boolean isWhitespace(int ch) {
    return (' ' == ch) || ('\n' == ch) || ('\r' == ch) || ('\t' == ch);
  } // isWhiteSpace(int)

   /**
   * Parse JSONString from a reader, keeping track of the current position
   */
  static JSONString parseString(Reader source) throws ParseException, IOException {
    int ch;
    ch = skipWhitespace(source);
    if (-1 == ch) {
      throw new ParseException("Unexpected end of file", pos);
    } // if 
    String s = "";
    char c = (char) ch;
    while(ch != -1 && c != '\"'){
      s += c;
      c = nextChar(source);
      while(ch == '\\'){
        c = nextChar(source);
        s += slash(c);
        c = nextChar(source);
      } // while
    } // while
    return new JSONString(s);
  } // parseString(source)

   /**
   * Parse JSONValue from a reader, keeping track of the current position
   */
  static JSONValue parseNumber(Reader source, int first) throws ParseException, IOException {
    int num = 0;
    char c = (char) first;
    String s = "";
    while(num != -1 && num != ']' && num != ',' && num != '}'){
      s += c;
      num = skipWhitespace(source);
      c = (char) num;
    } // while
    incrementReader(source, -1);
    if(s.indexOf('.') == -1 && s.indexOf('e') == -1 && s.indexOf('E') == -1){
      return new JSONInteger(s);
    } // if
    else{
      return new JSONReal(s);
    } // else
  } // parseNumber(source, first)

   /**
   * Parse JSONArray from a reader, keeping track of the current position
   */
  static JSONArray parseArray(Reader source) throws ParseException, IOException {
    JSONArray arr = new JSONArray();
    int ch;
    ch = skipWhitespace(source);
    incrementReader(source, -1);
    if(ch == ']'){
      return arr;
    } // if 
    while(ch != -1 && ch != ']') {
      arr.add(parseKernel(source));
      ch = skipWhitespace(source);
    } // while
    return arr;
  } // parseArray(source)

   /**
   * Parse JSONHash from a reader, keeping track of the current position
   */
  static JSONHash parseHash(Reader source) throws ParseException, IOException {
    JSONHash hash = new JSONHash();
    int ch;
    ch = skipWhitespace(source);
    if(ch == '}'){
      return hash;
    } // if
    incrementReader(source, -1);
    JSONString key;
    JSONValue value;
    while(ch != -1 && ch != '}') {
      key = (JSONString) parseKernel(source);
      ch = skipWhitespace(source);
      value = parseKernel(source);
      ch = skipWhitespace(source);
      hash.set(key, value);
    } // while
    return hash;
  } // parseHash(source)

  /**
   * increaments the the reader
   * @param source
   * @param i
   * @throws IOException
   */
  static public void incrementReader(Reader source, int i) throws IOException{
    pos += i;
    source.reset();
    source.skip(pos);
  } // incrementReader(source, i)

  /**
   * returns the next character
   */
  static public char nextChar(Reader source) throws IOException{
    int ch = source.read();
    ++pos;
    return (char) ch;
  } // nextChar(source)

  /**
   * Returns a string by checking the char c. 
   * @param c
   * @return
   */
  static public String slash(char c){
    String s = "";
    if(c == '\"'){
      s += "\\\"";
    } // if
    else if(c == '\\'){
      s += "\\\\";
    } // else if
    else if(c == '\b'){
      s += "\\b";
    } // else if
    else if(c == '\f'){
      s += "\\f";
    } // else if 
    else if(c == '\n'){
      s += "\\n";
    } // else if 
    else if(c == '\r'){
      s += "\\r";
    } // else if 
    else if(c == '\t'){
      s += "\\t";
    } // else if 
    else if(c == '\''){
      s += "\\\'";
    } // else if
    return s;
  } // slash(c)

} // class JSON
