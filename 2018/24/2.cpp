#include <algorithm>
#include <cstdio>
#include <cstring>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <tuple>
#include <vector>
using namespace std;

enum class Kind { GOOD, BAD };
enum class Type { FIRE, RADIATION, BLUDGEONING, SLASHING, COLD };

struct Group {
  Kind kind;
  int units;
  int hit_points;
  int attack_damage;
  Type attack;
  int initiative;
  set<Type> weak;
  set<Type> imune;

  Group(Kind _kind, int _units, int _hit_points, int _attack_damage,
        Type _attack, int _initiative, set<Type> _weak, set<Type> _imune)
      : kind(_kind),
        units(_units),
        hit_points(_hit_points),
        attack_damage(_attack_damage),
        attack(_attack),
        initiative(_initiative),
        weak(_weak),
        imune(_imune) {}

  int effective_power() const { return units * attack_damage; }

  int deals_damage(const Group &other) const {
    int mul = 1;
    if (other.weak.find(attack) != other.weak.end()) {
      mul = 2;
    }

    if (other.imune.find(attack) != other.imune.end()) {
      mul = 0;
    }

    return mul * effective_power();
  }
};

struct GroupSelectView {
  int id;
  Group *group;

  friend bool operator<(const GroupSelectView &a, const GroupSelectView &b) {
    if (a.group->effective_power() == b.group->effective_power())
      return a.group->initiative > b.group->initiative;
    return a.group->effective_power() > b.group->effective_power();
  }
};

struct GroupAttackView {
  int id;
  Group *group;

  friend bool operator<(const GroupAttackView &a, const GroupAttackView &b) {
    return a.group->initiative > b.group->initiative;
  }
};

void Test(vector<Group> &groups) {
  groups.push_back({Kind::GOOD,
                    17,
                    5390,
                    4507,
                    Type::FIRE,
                    2,
                    {Type::RADIATION, Type::BLUDGEONING},
                    {}});

  groups.push_back({Kind::GOOD,
                    989,
                    1274,
                    25,
                    Type ::SLASHING,
                    3,
                    {Type ::BLUDGEONING, Type::SLASHING},
                    {Type ::FIRE}});

  groups.push_back(
      {Kind::BAD, 801, 4706, 116, Type::BLUDGEONING, 1, {Type::RADIATION}, {}});

  groups.push_back({Kind::BAD,
                    4485,
                    2961,
                    12,
                    Type::SLASHING,
                    4,
                    {Type::FIRE, Type::COLD},
                    {Type::RADIATION}});

  return;
}

void Second(vector<Group> &groups) {
  groups.push_back({Kind::GOOD, 123, 8524, 612, Type::SLASHING, 11, {}, {}});
  groups.push_back({Kind::GOOD,
                    148,
                    4377,
                    263,
                    Type::COLD,
                    1,
                    {Type::SLASHING, Type::BLUDGEONING},
                    {}});
  groups.push_back(
      {Kind::GOOD, 6488, 2522, 3, Type::BLUDGEONING, 19, {Type::FIRE}, {}});
  groups.push_back({Kind::GOOD,
                    821,
                    8034,
                    92,
                    Type::COLD,
                    17,
                    {},
                    {Type::COLD, Type::BLUDGEONING}});
  groups.push_back(
      {Kind::GOOD, 1163, 4739, 40, Type::BLUDGEONING, 14, {Type::COLD}, {}});
  groups.push_back({Kind::GOOD,
                    1141,
                    4570,
                    32,
                    Type::RADIATION,
                    18,
                    {Type::FIRE, Type::SLASHING},
                    {}});
  groups.push_back({Kind::GOOD, 108, 2954, 262, Type::RADIATION, 8, {}, {}});
  groups.push_back({Kind::GOOD,
                    4752,
                    6337,
                    13,
                    Type::COLD,
                    20,
                    {Type::BLUDGEONING, Type::COLD},
                    {Type::SLASHING}});

  groups.push_back(
      {Kind::GOOD, 4489, 9894, 20, Type::SLASHING, 12, {Type::SLASHING}, {}});
  groups.push_back({Kind::GOOD, 331, 12535, 300, Type::SLASHING, 15, {}, {}});

  groups.push_back({Kind::BAD,
                    853,
                    13840,
                    26,
                    Type::FIRE,
                    3,
                    {Type::BLUDGEONING, Type::COLD},
                    {}});
  groups.push_back(
      {Kind::BAD, 450, 62973, 220, Type::FIRE, 13, {Type::SLASHING}, {}});
  groups.push_back(
      {Kind::BAD, 3777, 35038, 18, Type::RADIATION, 7, {Type::COLD}, {}});
  groups.push_back({Kind::BAD,
                    96,
                    43975,
                    862,
                    Type::RADIATION,
                    16,
                    {Type::COLD, Type::SLASHING},
                    {Type::BLUDGEONING}});
  groups.push_back({Kind::BAD,
                    1536,
                    14280,
                    18,
                    Type::SLASHING,
                    2,
                    {Type::COLD, Type::FIRE},
                    {Type::BLUDGEONING}});
  groups.push_back({Kind::BAD,
                    3696,
                    36133,
                    18,
                    Type::BLUDGEONING,
                    10,
                    {Type::RADIATION},
                    {Type::COLD, Type::FIRE}});
  groups.push_back(
      {Kind::BAD, 3126, 39578, 22, Type::RADIATION, 4, {Type::COLD}, {}});
  groups.push_back({Kind::BAD,
                    1128,
                    13298,
                    23,
                    Type::FIRE,
                    6,
                    {Type::BLUDGEONING, Type::SLASHING},
                    {}});
  groups.push_back({Kind::BAD,
                    7539,
                    6367,
                    1,
                    Type::SLASHING,
                    5,
                    {Type::FIRE},
                    {Type::RADIATION}});
  groups.push_back({Kind::BAD,
                    1886,
                    45342,
                    45,
                    Type::COLD,
                    9,
                    {Type::FIRE, Type::COLD},
                    {}});
}

int check(int boost) {
  vector<Group> groups;
  // Test(groups);
  Second(groups);
  int n = groups.size();

  for (auto &g : groups) {
    if (g.kind == Kind::GOOD) {
      g.attack_damage += boost;
    }
  }

  vector<GroupSelectView> select_view;
  vector<GroupAttackView> attack_view;

  for (int i = 0; i < n; ++i) {
    select_view.push_back({i, &groups[i]});
    attack_view.push_back({i, &groups[i]});
  }

  auto check_alive = [&groups](Kind kind) {
    int alive = 0;
    for (auto &g : groups) {
      if (g.kind == kind && g.units > 0) alive++;
    }
    return alive > 0;
  };

  auto count_units = [&groups](Kind kind) {
    int alive = 0;
    for (auto &g : groups) {
      if (g.kind == kind && g.units > 0) alive += g.units;
    }
    return alive;
  };

  while (1) {
    if (!check_alive(Kind::GOOD) || !check_alive(Kind::BAD)) break;

    map<int, int> attack;
    map<int, int> defends;

    sort(select_view.begin(), select_view.end());

    for (int k = 0; k < n; ++k) {
      int i = select_view[k].id;
      if (groups[i].units <= 0) continue;

      int attack_id = -1, max_damage = 0;

      for (int l = 0; l < n; ++l) {
        int j = select_view[l].id;

        if (i == j || groups[i].kind == groups[j].kind) continue;
        if (groups[j].units <= 0) continue;

        int damage = groups[i].deals_damage(groups[j]);
        if (defends.find(j) == defends.end() && damage > max_damage) {
          max_damage = damage;
          attack_id = j;
        }
      }

      if (attack_id != -1) {
        attack[i] = attack_id;
        defends[attack_id] = i;
      }
    }

    sort(attack_view.begin(), attack_view.end());
    int killed = 0;

    for (int k = 0; k < n; ++k) {
      int i = attack_view[k].id;
      if (groups[i].units <= 0) continue;

      if (attack.find(i) != attack.end()) {
        int j = attack[i];

        int damage = groups[i].deals_damage(groups[j]);
        int units_killed = damage / groups[j].hit_points;
        killed += units_killed;

        groups[j].units -= units_killed;
      }
    }

    if (killed == 0) break;
  }

  if (check_alive(Kind::GOOD) && check_alive(Kind::BAD)) return -1;
  if (!check_alive(Kind::GOOD)) return -1;
  return count_units(Kind::GOOD);
}
int main(int argc, char **argv) {
  int lo = 0, hi = 1 << 20;
  int sol;
  while (lo < hi) {
    int mid = (lo + hi) >> 1;
    int units = check(mid);
    if (units == -1) {
      lo = mid + 1;
    } else {
      hi = mid;
      sol = units;
    }
  }
  cout << sol << endl;
  return 0;
}
