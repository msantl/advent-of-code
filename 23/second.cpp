#include <cstdio>
#include <iostream>

using namespace std;

inline bool is_prime(int x) {
  if (x % 2 == 0) return false;
  for (int i = 3; i * i < x; i += 2) {
    if (x % i == 0) return false;
  }
  return true;
}

int main(int argc, char **argv) {
  int b = 106700;
  int sol = 0;
  for (int x = 0; x <= 1000; ++x) {
    if (!is_prime(b)) {
      sol++;
    }

    b += 17;
  }
  cout << sol << endl;
  return 0;
}
