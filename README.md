# Incquery-d allocation
is the resource allocation component for the distributed model query engine, [IncQuery-D](https://github.com/viatra/incqueryd).

The project is mainly written in Java, Scala and Xtend, while the computation of optimization problems is mplemented in the domain-specific CPLEX OPL language. The project uses Maven for dependency management, building and packaging.

It can be either used as a standalone service (build [server project](https://github.com/viatra/incqueryd-allocation/tree/master/hu.bme.mit.incqueryd.allocation.server)), as an Eclipse plug-in (build [tooling project](https://github.com/viatra/incqueryd-allocation/tree/master/hu.bme.mit.incqueryd.tooling)), or as a library embedded to other applciations (build [csp project](https://github.com/viatra/incqueryd-allocation/tree/master/hu.bme.mit.incqueryd.allocation.csp)).
From a separate application, one could interact with the [CplexAllocationSolver class](https://github.com/viatra/incqueryd-allocation/blob/master/hu.bme.mit.incqueryd.allocation.csp/src/hu/bme/mit/incqueryd/csp/cplex/CplexAllocationSolver.java) which provides a simple interface for computing allocation solutions.

## Implemented strategies
Two different resource allocation optimization strategies are implemented currently. The common in both is that resource constraints (which currently are that processes shouldn't exceed the memory capacity of the machines to a certain extent) are taken into account.
The differences are in the allocation optimization objectives:

1. [Communication optimization](https://github.com/viatra/incqueryd-allocation/blob/master/cplex.models/commopt.mod) aims to reduce query evaluation time by reducing the amount of data transmission on expensive remote links in the network.
2. [Cost optimization](https://github.com/viatra/incqueryd-allocation/blob/master/cplex.models/costopt.mod) aims to reduce infrastructure costs by using as few and as cheap machiens as possible.
