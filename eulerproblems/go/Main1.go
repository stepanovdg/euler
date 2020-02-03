package main

import (
	"fmt"
	"sync"
)

func multiplierProducer(c chan<- int, mult int, wg *sync.WaitGroup) {

	fmt.Println("in producer %d", mult)
	for i := 1; (i * mult) < 1000; i++ {
		fmt.Println("in producer %d send value %d", mult, i*mult)
		c <- i * mult
	}
	fmt.Println("in producer %d all is done", mult)
	wg.Done()
}

func filterEven(c chan <- int) (chan int){
	out:= make(chan int)


	return out
}

func application1(){
	var wg sync.WaitGroup
	wg.Add(3)

	sum := 0
	c := make(chan int)
	d := make(chan int)
	s := make(chan bool, 1)

	go multiplierProducer(c, 3, &wg)
	go multiplierProducer(c, 5, &wg)
	go multiplierProducer(d, 15, &wg)

	go func(wg *sync.WaitGroup, c chan int) {
		fmt.Println("waiting to close")
		wg.Wait()
		fmt.Println("closing channel")
		close(c)
		close(d)
		s <- true
	}(&wg, c)

	for {
		select {
		case i := <-c:
			//fmt.Printf("in main + %d prev sum =%d \n", i, sum)
			sum += i
		case i := <-d:
			//fmt.Printf("in main - %d prev sum =%d \n", i, sum)
			sum -= i
		case <-s:
			fmt.Println(sum)
			return
		}

	}
}

func main() {
 	//application1()
	application2()

}

func application2(){

}
