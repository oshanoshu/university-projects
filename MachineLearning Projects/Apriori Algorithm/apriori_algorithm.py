#!/usr/bin/env python
# coding: utf-8


import csv
import collections
import itertools 
from itertools import islice


# Importing the CSV file with all the transactions and converting it into the list of sets where each set is a transaction



file = "SuperCenterDataNew.csv"
transactions = []
with open(file, 'r') as f:
    trans = csv.reader(f, delimiter=',')
    for line in trans:
        while("" in line) : 
            line.remove("") 
        transactions.append(frozenset(line))

# Since there are lots of transactions we'll just take some chunk of data to test our apriori algorithm


test_transactions=islice(transactions,500)
test_transactions=list(test_transactions)
test_transactions


# The functions for the finding the subsets and Apriori algorithm:


def findsubsets(S,m):
    return [set(i) for i in itertools.combinations(S, m)]
def apriori(abc,minsupport):
    level=1
    bc=abc[:]
    dc=bc[:]
    def frequent_itemset(one,level,two):
        
        #get the list of all the unique items from the list passed
        unique_item=[]
        for line in one:
            for item in line:
                unique_item.append(item)
        unique_item=set(unique_item)
        
        #finding subset from the unique items
        subset_from_items=findsubsets(unique_item, level)
        
        #finding frequent itemset
        frequent_from_subset=[]
        
        #new dictionary for storing the only itemset valid for the next loop
        new_wholeset={}
       #medium=set() 
        for item in subset_from_items:
            i=0
            for items in two:
                if(item.issubset(items)):
                    i=i+1
                    new_wholeset.update({frozenset(items):i})
            if(i>=minsupport):
                frequent_from_subset.append(item)
             #  result=all(elem in new_wholeset for elem in medium)
             #  if (result==False):
            #     new_wholeset.extend(medium)
        
        #printing the frequent itemsets and passing the arguments for recursion
        if(len(frequent_from_subset)>1):
            print("The "+str(level)+" item set with minimum support of "+str(minsupport)+" are: \n")
            for items in frequent_from_subset:
                print(items)
            level=level+1
            
            #function call
            frequent_itemset(frequent_from_subset,level,list(new_wholeset.keys()))
        
        elif(len(frequent_from_subset)==1):
            print("The "+str(level)+" item set with minimum support of "+str(minsupport)+" are: \n")
            for items in frequent_from_subset:
                print(items)
                
    #function call initialized            
    frequent_itemset(bc,level,dc)
    return "Done"












