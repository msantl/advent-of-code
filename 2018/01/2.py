def main():
    seen = {}
    sol = None
    c = 0

    with open("2.in") as f:
        lines = f.readlines()

        while sol is None:
            for line in lines:
              c += int(line.strip())

              if not sol and c in seen:
                  sol = c
                  break

              seen[c] = 1;

    print(sol)


if __name__=="__main__":
  main()
