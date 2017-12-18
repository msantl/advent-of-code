#include <climits>
#include <cstdio>
#include <iostream>
#include <set>
#include <vector>

using namespace std;

#define PRIME 31

int hash_banks(const vector<int>& banks) {
  int hash = 0;
  for (const auto& b : banks) {
    hash = hash * 31 + b;
  }
  return hash;
}

int main(int argc, char** argv) {
  vector<int> banks;
  set<int> cycle;

  char buffer[1024];
  while (fgets(buffer, sizeof(buffer), stdin)) {
    int block = 0;
    bool non_space = false;
    for (char* c = buffer; *c; ++c) {
      if ('0' <= *c && *c <= '9') {
        block = 10 * block + (*c - '0');
        non_space = true;
      } else if (non_space) {
        banks.push_back(block);
        block = 0;
        non_space = false;
      }
    }
  }

  int n = banks.size(), cnt = 0;
  cycle.insert(hash_banks(banks));

  while (1) {
    int max_el = -1, max_id = -1;
    for (int i = 0; i < n; ++i) {
      if (max_id == -1 || (max_el < banks[i])) {
        max_id = i;
        max_el = banks[i];
      }
    }

    banks[max_id] = 0;

    for (int i = (max_id + 1) % n; max_el > 0; i = (i + 1) % n) {
      banks[i] += 1;
      max_el -= 1;
    }

    cnt += 1;

    int hash = hash_banks(banks);
    if (cycle.count(hash)) {
      break;
    }

    cycle.insert(hash);
  }

  cout << cnt << endl;

  return 0;
}
