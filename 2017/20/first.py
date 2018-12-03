import sys

def split_triplets(x):
    return [int(y) for y in x[3:-1].split(",")]

def distance(p):
    (x, y, z) = p
    return abs(x) + abs(y) + abs(z)

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

        N += 1

    for i in range(10000):
        for j in range(N):
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

    min_dist = None
    sol = 0

    for j in range(N):
        dist = distance(coords[j])

        if min_dist is None or min_dist > dist:
            min_dist = dist
            sol = j

    print(sol, min_dist)

if __name__=="__main__":
    main()
