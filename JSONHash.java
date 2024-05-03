import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

/**
 * JSON hashes/objects.
 * @author Connor Heagy
 * @author Alex Maret
 */
public class JSONHash implements JSONValue {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  ChainedHashTable<JSONString, JSONValue> hashTable;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  public JSONHash() {
    this.hashTable = new ChainedHashTable<JSONString, JSONValue>();
  } // ChainedHashTable

  // +-------------------------+-------------------------------------
  // | Standard object methods |
  // +-------------------------+

  /**
   * Convert to a string (e.g., for printing).
   */
  public String toString() {
    Iterator<KVPair<JSONString,JSONValue>> next = hashTable.iterator();
    String result = "{"; 
    for (int i = 0; i < hashTable.size() - 1; i++) {
      result += next.next().toString() + ", ";
    } // for 
    if (hashTable.size() != 0) {
      result += next.next().toString();
    } // if
    result += "}";
    return result;
  } // toString()

  /**
   * Compare to another object.
   */
  public boolean equals(Object other) {
    if(other instanceof JSONHash){
      JSONHash j = (JSONHash) other;
      return hashTable.equals(j.getHash());
    } // if
    return false;
  } // equals(Object)

  /**
   * Compute the hash code.
   */
  public int hashCode() {
    return hashTable.hashCode();
  } // hashCode()

  // +--------------------+------------------------------------------
  // | Additional methods |
  // +--------------------+

  /**
   * Write the value as JSON.
   */
  public void writeJSON(PrintWriter pen) {
    pen.println(toString());
  } // writeJSON(PrintWriter)

  /**
   * Get the underlying value.
   */
  public Iterator<KVPair<JSONString,JSONValue>> getValue() {
    return this.iterator();
  } // getValue()

  // +-------------------+-------------------------------------------
  // | Hashtable methods |
  // +-------------------+

  /**
   * Get the value associated with a key.
   */
  public JSONValue get(JSONString key) {
    return hashTable.get(key);
  } // get(JSONString)

  /**
   * Get all of the key/value pairs.
   */
  public Iterator<KVPair<JSONString,JSONValue>> iterator() {
    return hashTable.iterator();
  } // iterator()

  /**
   * Set the value associated with a key.
   */
  public void set(JSONString key, JSONValue value) {
    hashTable.set(key, value);
  } // set(JSONString, JSONValue)

  /**
   * Find out how many key/value pairs are in the hash table.
   */
  public int size() {
    return hashTable.size();
  } // size()

  public ChainedHashTable<JSONString, JSONValue> getHash(){
    return hashTable;
  }

} // class JSONHash
