# Drevo Stochastic
There are many problems that found their solutions is intractable or even can not be solved analiticaly and because that for this kind of problem there is the possibility try other way to handle the situation, that is, using an stochastic methods to find good solutions for problems. This project intention is provide a set of this kind of tools to apply in real problem.

algorithms like that is better know not as optimization algorithm, but a search one, because they give no garantee to found the best solution, but a better solution from the point from where algorithm start the search. It means, from the space solution we have the domine of the problem and a point of it is gived as the solution candidate, from it this point the algorithm try to improve the value based on the objective function and if the problem need to maximize or minimize the function.

There are many documentation about how each algorithm is implemented in Javadoc from this project, but here is offered an introdution and sample to how use the algorithms to sove a real problem.

## Simulated Annealing
From Javadoc about SimulatedAnnealing class and other classes involved in this implementaion is possible read many informations about how use it to solve problem. Below is offered an way to solve the Traveling Salesman Problem, that is the problem to visit a set of cities in a shortest path possible, reducing the traveling's distance. To solve this problem using SimulatedAnnealing class, first is necessary define the objective function to compute the distance folow to visit all cities.

The sequence of implementation needed to folow to solve this problem is implement AnnealingFunction to compute the cost to visit the cities using the path sugested, then define the parameters to create AnnealingContext and if wanted implement an StateChangeHandle implementation in the case the state of changes during the process wanted to be know. With that is possible to use SimulatedAnnealing to solve the problem.