#include <cstdlib>
#include <iostream>
#include <string>
using namespace std;

int main(int argc, char** argv) {
  string in;
  cin >> in;

  int n = in.size();
  in += in;
  int sum = 0;

  for (int i = 0; i < n; ++i) {
    if (in.at(i) == in.at(i + 1)) {
      sum += in.at(i) - '0';
    }
  }

  cout << sum << endl;
  return 0;
}
