package main

import (
	"flag"
	"fmt"
	"time"
)

var n int

func init() {
	flag.IntVar(&n, "n", 0, "init value")
}

func main() {
	flag.Parse()
	startTime := time.Now().Unix()
	result := fib2(n)
	end := time.Now().Unix()
	fmt.Printf("time %d, %v\n", (end - startTime), result)
}

func fib1(n int) int {
	if n <= 1 {
		return n
	}
	return fib1(n-1) + fib1(n-2)
}

func fib2(n int) int {
	if n <= 1 {
		return n
	}
	first := 0
	second := 1
	for i := 0; i < n-1; i++ {
		sum := first + second
		first = second
		second = sum
	}
	return second
}
