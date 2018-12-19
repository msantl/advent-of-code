#include <algorithm>
#include <cstdio>
#include <cstring>
#include <functional>
#include <iostream>
#include <map>
#include <string>

using namespace std;

int main(int argc, char **argv) {
  long long N = 10551358LL;
  long long sol = 0;

  for (long long i = 1; i <= N; ++i) {
    if (N % i == 0) sol += i;
  }

  cout << sol << endl;
  return 0;
}
