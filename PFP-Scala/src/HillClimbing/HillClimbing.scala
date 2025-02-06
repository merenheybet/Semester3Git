abstract class Solution {
  def quality: Double // Qualitaet der Loesung
  def neighbors: List[Solution] // Benachbarte Loesungen
}

def findBest: Solution => Solution = s =>
  s.neighbors.foldLeft(s)((o,n) => if (n.quality >= o.quality) n else o)

def hillClimbing: Solution => LazyList[Solution] = s =>
  def helper: Solution => (Solution => Solution) => LazyList[Solution] = {
    s => f => f(s) #:: helper(f(s))(f)
  }
  s #:: helper(s)(findBest)

def optimum: LazyList[Solution] => Solution = sls =>
  sls.dropWhile(sol => !sol.equals(findBest(sol))).head

def swapNeighbors: List[Int] => List[List[Int]] = ps =>
  ps.zip(List.range(0,ps.length)).flatMap((_, i) => for(index <- List.range(i+1, ps.length) if (index >= i)) yield swap(ps, index, i))


// Vertauscht Elemente der Liste ls an den Indizes i und j.
// (i, j >= 0)
def swap[E]: (List[E], Int, Int) => List[E] = (ls, i, j) => //-...-//
  ls.updated(i, ls(j)).updated(j, ls(i))