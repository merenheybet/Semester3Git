def find[E]: (List[E], E) => List[Int] =
  case (Nil, el) => Nil
  case (list, Nil) => Nil
  case (list, el) if (!list.contains(el)) => Nil
  case (list, el) if (list.contains(el)) => findHelper(list, el, 0)

def findHelper[E]: (List[E], E, Int) => List[Int] =
  case (Nil, el, in) => Nil
  case (head::tail, el, in) if(head.equals(el)) => in::findHelper(tail, el, in+1)
  case (head :: tail, el, in) => findHelper(tail, el, in+1)

def powerSet[E]: List[E] => List[List[E]] =
  case Nil => List(Nil)
  case head::tail =>
    val tailPowerSet = powerSet(tail)
    tailPowerSet ::: tailPowerSet.map(head::_)
