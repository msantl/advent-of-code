def main():
    c = 0
    with open("1.in") as f:
        for line in f.readlines():
            c += int(line.strip())

    print(c)


if __name__=="__main__":
  main()
