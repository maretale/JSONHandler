# expert-octo-broccoli-MP10
Alexander Maret and Connor Heagy
May 3, 2024
Acknowledgements:
Tim - Discussed JSONString toString with us and made us realize we had done it all wrong! We want to print "\\n" when we see a "\n".
Sam - Helped us know what toString should return in our JSONString class. He helped when our writeJSON wasn't working as expected when working with arrays of strings. He helped us with how to take our iterator we built in the ChainedHashTable class and use it in our JSONHash class. We had to change every reference to Pair<K,V> to KVPair<K,V>.
Peter - Attempted to solve our problem with writeJSON.
CSC207 Website - provided instructions for MP10
JSON Standard Website: https://www.json.org/json-en.html
JSON Standard Website: https://ecma-international.org/publications-and-standards/standards/ecma-404/

This project can read a String, integer, real, constant, array, or object written using the JSON standard and store it. It is capable of copmaring and rewriting objects stored this way.
