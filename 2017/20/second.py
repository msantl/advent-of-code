import sys

def split_triplets(x):
    return [int(y) for y in x[3:-1].split(",")]

def distance(p):
    (x, y, z) = p
    return abs(x) + abs(y) + abs(z)

def hash(p):
    (x, y, z) = p
    return x * 10017 + y * 3131 + z

def main():
    N = 0
    coords = []
    speeds = []
    accelr = []
    active = []

    for line in sys.stdin:
        line = line.strip().split(", ")

        coords.append(split_triplets(line[0]))
        speeds.append(split_triplets(line[1]))
        accelr.append(split_triplets(line[2]))
        active.append(True)

        N += 1

    for i in range(1000):
        memo = {}
        for j in range(N):
            if not active[j]:
                continue

            (xa, ya, za) = accelr[j]

            (xs, ys, zs) = speeds[j]
            (xc, yc, zc) = coords[j]

            xs += xa
            ys += ya
            zs += za

            xc += xs
            yc += ys
            zc += zs

            speeds[j] = (xs, ys, zs)
            coords[j] = (xc, yc, zc)

            h = hash(coords[j])
            if h in memo:
                memo[h].append(j)
            else:
                memo[h] = [j]

        for coord in memo.keys():
            if len(memo[coord]) > 1:
                for k in memo[coord]:
                    active[k] = False


    print(sum(active))

if __name__=="__main__":
    main()
