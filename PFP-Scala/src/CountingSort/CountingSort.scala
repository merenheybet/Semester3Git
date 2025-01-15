def incCount: (Int, List[Int]) => List[Int] =
  case (a, b) if b.length <= a => incCount(a, b ::: List(0))
  case (a, b) => b.take(a) ::: List(b(a) + 1) ::: b.drop(a + 1)

def getCounts: List[Int] => List[Int] =
  case Nil => Nil
  case a::tail =>  incCount(a, getCounts(tail))

def countingSort: List[Int] => List[Int] =
  case Nil => Nil
  case a => countingSortHelper(getCounts(a), 0)

def countingSortHelper: (List[Int], Int) => List[Int] =
  case (Nil,_) => Nil
  case (currentCount::tail, number) =>
    if currentCount > 0 then number::countingSortHelper((currentCount-1)::tail, number)
    else countingSortHelper(tail, number + 1)