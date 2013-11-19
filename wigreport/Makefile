pdf: dvi
	dvips -o wigreport.ps -Ppdf -G0 -t letter wigreport.dvi
	ps2pdf -dMaxSubsetPct=100 -dSubsetFonts=true -dEmbedAllFonts=true wigreport.ps wigreport.pdf

dvi:
	latex wigreport
	latex wigreport
# uncomment if you cite things
#	bibtex wigreport
#	latex wigreport

clean:
	rm -f text/*~
	rm -f bib/*~
	rm -f figs/*~
	rm -f *~
	rm -f *.aux *.bbl *.blg *.log
