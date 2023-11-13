#small script to add the number of the rule in the grammar file wihout having to do it manually

with open ("doc/grammar_doc/final_grammar.txt", "r") as myfile:
    data=myfile.readlines()

"""the file is in the following format:
[nb] <rule>     -> rule
"""
# we want that the numbes are consecitive   


for i in range(len(data)-1):
    #count the number of digits
    j = 0
    while(data[i][j] != "]"):
        j+=1
    
    data[i] = "[" + str(i+1) + "]" + data[i][j+1:]

        
        

with open("doc/grammar_doc/final_grammar.txt", "w") as myfile:
    myfile.writelines(data)