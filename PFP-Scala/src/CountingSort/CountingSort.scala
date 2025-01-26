def incCount: (Int, List[Int]) => List[Int] =
  case (0, Nil) => List(1)
  case (0, a::b) => (a+1)::b
  case (i, Nil) => 0::incCount(i-1, Nil)
  case (i, a::b) => a::incCount(i-1, b)

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