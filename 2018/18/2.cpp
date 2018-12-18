#include <cstdio>
#include <cstring>
#include <algorithm>
#include <iostream>
#include <vector>
#include <tuple>
#include <unordered_map>

using namespace std;

const int MAX = 51;

int dx[] = { 0, 0, -1, -1, -1, 1,  1, 1};
int dy[] = {-1, 1,  0, -1,  1, 0, -1, 1};

char grid[MAX][MAX];
int r, s;
char temp[MAX][MAX];

tuple<int,int,int> count(int x, int y) {
    int wood = 0, lumber = 0, open = 0;
    for (int i = 0; i < 8; ++i) {
        int nx = x + dx[i];
        int ny = y + dy[i];

        if (nx < 0 || nx >= r || ny < 0 || ny >= s) continue;
        if (temp[nx][ny] == '|') ++wood;
        if (temp[nx][ny] == '#') ++lumber;
        if (temp[nx][ny] == '.') ++open;
    }
    return {wood, lumber, open};
}

void simulate() {
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < s; ++j) {
            temp[i][j] = grid[i][j];
        }
    }

    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < s; ++j) {
            int wood, lumber, open;
            tie(wood, lumber, open) = count(i, j);
            if (temp[i][j] == '.' ) {
                if (wood >= 3) grid[i][j] = '|';
                else grid[i][j] = temp[i][j];
            } else if (temp[i][j] == '|') {
                if (lumber >= 3) grid[i][j] = '#';
                else grid[i][j] = temp[i][j];
            } else if (temp[i][j] == '#') {
                if (lumber < 1 || wood < 1) grid[i][j] = '.';
                else grid[i][j] = temp[i][j];
            }
        }
    }

    return;
}

long long my_hash() {
    long long h = 0;
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < s; ++j) {
            int v = 0;
            if (grid[i][j] == '.')  v = 1;
            if (grid[i][j] == '|')  v = 2;
            if (grid[i][j] == '#')  v = 3;
            h = h * 5 + v;
        }
    }
    return h;
}

unordered_map<long long, long long> cycle;

int main(int argc, char **argv) {
    memset(grid, 0, sizeof grid);
    r = s = 0;
    while (scanf("%[^\n]\n", grid[r]) != EOF) {
        s = max(s, (int) strlen(grid[r]));
        r ++;
    }

    for (long long k = 1; k <= 1000000000; ++k) {
        simulate();
        auto h = my_hash();
        if (cycle[h] > 0) {
            long long made = k - cycle[h];
            while (k + made <= 1000000000) k += made;
            cycle.clear();
        } else {
            cycle[h] = k;
        }
    }

    int wood = 0;
    int lumber = 0;

    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < s; ++j) {
            if (grid[i][j] == '|') ++wood;
            if (grid[i][j] == '#') ++lumber;
        }
    }

    cout << wood << " " << lumber << endl;
    cout << wood * lumber << endl;
    return 0;
}
