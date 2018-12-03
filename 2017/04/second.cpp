#include <algorithm>
#include <cctype>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <iostream>
#include <set>
using namespace std;

int main(int argc, char **argv) {
  char buffer[2048];
  int sol = 0;
  while (fgets(buffer, sizeof(buffer), stdin)) {
    string word;
    set<string> passphrase;
    bool is_valid = true;

    for (char *c = buffer; *c; ++c) {
      if (isalnum(*c)) {
        word += *c;
      } else {
        sort(word.begin(), word.end());

        if (passphrase.count(string(word))) {
          is_valid = false;
          break;
        }
        passphrase.insert(word);
        word = string();
      }
    }
    if (is_valid) {
      sol++;
    }
  }

  cout << sol << endl;

  return 0;
}
