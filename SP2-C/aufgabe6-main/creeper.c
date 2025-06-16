#include "argumentParser.h"

// Usable functions:
// [] strtol(3p) -- str to int
// [] basename(3p) -- return the last component of given path (für -name) 
// [] fnmatch(3p) -- compare reference with wildcard to something
// [] lstat(3p) -- liefert Informationen über den SymLink selber
// [] opendir(3p) -- open directory(Iterator) -- returns a DIR * 
// [] readdir(3p) -- read directory -- struct dirent *readdir(DIR*)
// [] closedir(3p) -- close directory -- int closedir(DIR*)

int creeper(){
	//irgendwas
}


int main(int argc, char** argv){
	// Initialise Befehlszeilenparser
	// verarbeite übergegebenen Operationen

	// -name: mit Shell-Wildcard-Muster vergleichen (verwende fnmatch(3p))
	// -type: eingrenzen auf Verzeichnisse(-type = d) oder reguläre Dateien(-type = f)
	// -maxdepth: maximale Suchtiefe
}

