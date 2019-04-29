# importing basic libraries.
import pandas as pd
from apyori import apriori
from itertools import combinations,chain
import numpy as np
import itertools
import csv


def joinset(itemset, k):
    return set([i.union(j) for i in itemset for j in itemset if len(i.union(j)) == k])


# In[413]:


def subsets(itemset):
    return chain(*[combinations(itemset, i + 1) for i, a in enumerate(itemset)])


# In[414]:


def itemset_from_data(data):
    itemset = set()
    transaction_list = list()
    for row in data:
        transaction_list.append(frozenset(row))
        for item in row:
            if item:
                itemset.add(frozenset([item]))
    return itemset, transaction_list


# In[415]:


def itemset_support(transaction_list, itemset, min_support=0):
    len_transaction_list = len(transaction_list)
    l = [
        (item, float(sum(1 for row in transaction_list if item.issubset(row))) / len_transaction_list)
        for item in itemset
    ]
    return dict([(item, support) for item, support in l if support >= min_support])


# In[416]:


def freq_itemset(transaction_list, c_itemset, min_support):
    f_itemset = dict()

    k = 1
    while True:
        if k > 1:
            c_itemset = joinset(l_itemset, k)
        l_itemset = itemset_support(transaction_list, c_itemset, min_support)
        if not l_itemset:
            break
        f_itemset.update(l_itemset)
        k += 1

    return f_itemset


# In[417]:


# Function for itemset and the frequent itemset
def apriori(data, min_support, min_confidence):
    # Get first itemset and transactions
    itemset, transaction_list = itemset_from_data(data)

    # Get the frequent itemset
    f_itemset = freq_itemset(transaction_list, itemset, min_support)

    # Association rules
    rules = list()
    for item, support in f_itemset.items():
        if len(item) > 1:
            for A in subsets(item):
                B = item.difference(A)
                A = frozenset(A)
                AB = A | B
                confidence = float(f_itemset[AB]) / f_itemset[A]
                if confidence >= min_confidence:
                    rules.append((A, B, confidence))

    return f_itemset


# In[418]:


# Print the result for the dataset
def print_report(f_itemset):
    print('--Frequent Itemset--')
    for item, support in sorted(f_itemset.items(), key=lambda support: support[1]):
        print('[I] {} : {}'.format(tuple(item), round(support, 4)))


# In[ ]:


# In[419]:


# Main Function
def main():
    dataset = pd.read_csv('SuperCenterDataNew.csv')
    print(dataset.head())
    min_support = float(input("What is the minimum support "))
    min_confidence = 0.02
    itemset = apriori(dataset, min_support, min_confidence)
    print(itemset)


if __name__ == '__main__':
    main()




