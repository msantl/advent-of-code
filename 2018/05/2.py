import collections

def find_and_remove(poly):
    if len(poly) == 1:
        return poly

    res = ""
    i = 0
    while i < len(poly):
        if i + 1 < len(poly) and poly[i].lower() == poly[i+1].lower() and poly[i] != poly[i+1]:
            i += 1
        else:
            res += poly[i]

        i += 1
    return res

def remove(poly, x):
    res = ""
    i = 0
    while i < len(poly):
        if poly[i].lower() != x:
            res += poly[i]
        i += 1
    return res

def main():
    with open("2.in") as f:
        polymer = f.readline().strip()
        best = len(polymer)

        letters = set()
        for l in polymer:
            letters.add(l.lower())

        for l in letters:
            prev = None
            curr = remove(polymer, l)
            while prev != curr:
                prev = curr
                curr = find_and_remove(curr)

            if len(curr) < best:
                best = len(curr)

        print(best)


if __name__=="__main__":
    main()
