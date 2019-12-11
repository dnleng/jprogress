# The *jprogress* progression engine
The Java progression engine, called *jprogress*, implements support for the progression of MTL formulas with partial-state information.
It does so by keeping track of all the formulas progression could have reached, given the observed partial-state stream.
For more details, we refer to the following material discussing *jprogress*:
- de Leng, D., and Heintz., F. 2019. Approximate Stream Reasoning with Metric Temporal Logic under Uncertainty. In Proceedings of the 33rd AAAI Conference on Artificial Intelligence.
- de Leng, D., and Heintz., F. 2018. Partial-State Progression for Stream Reasoning with Metric Temporal Logic. In Proceedings of the 16th International Conference on Principles of Knowledge Representation and Reasoning.


## Installation
The software has been prepared as a Maven project in the IntelliJ IDEA IDE.
We recommend cloning the repository and opening the provided project files in IntelliJ IDEA.
To run a quick example, one can build a JAR artifact and progress the MTL formula `always ( not p -> (eventually [0,100] ( always [0,10] p ) ) )` with a `MAX_NODES` value of 180 as follows:
```console
$ java -Xms50g -Xmx50g -jar jprogressor.jar "output.csv" "false" "1" "180" "20" "always ( ( p ) or (eventually [0,100] ( always [0,10] p ) ) )"
```
This will generate log data in the `output.csv` file.


## Getting Started
The *jprogress* implementation in its current form does not yet provide an interactive mode using for example a command-line interface.
It is best regarded as a library.
To perform partial-state progression, one needs to provide an MTL formula, a stream generator, a progression strategy, and optional strategy-associated parameters.
The *jprogress* implementation will use the stream generator to generate a sequence of states, which it uses to progress the provided MTL formula.
The progression strategy determines how the progression graph is constructed and maintained over time.
We recommend using the LEAKY progression strategy, as described in the KR extended abstract.

As an example, we illustrate how one can reproduce the results shown in the KR extended abstract, where we measure the time to termination and the leaked probability at termination for a varying `MAX_NODES` value.
Note that `MAX_NODES` represents the maximum size of the progression graph in terms of vertices *at the end* of a progression step.
The first step is to construct a `ProgressorFactory`:
```java
// Construct a progressor factory
ProgressorFactory pf = new ProgressorFactory();
pf.setMaxNodes(maxNodes);
pf.setMaxTTL(ttl);
```
This sets the `MAX_NODES` and `MAX_TTL` values to `maxNodes` and `ttl`.
Using the `ProgressorFactory` we can then construct an MTL formula and pass it on to a `Progressor` in one of two ways.

By explicit construction:
```java
// Build an MTL formula and feed it to a progressor
Atom p = new Atom("p");
Formula formula = new Always(Integer.MAX_VALUE, new Disjunction(p, new Eventually(100, new Always(10, p))));
Progressor progressor = pf.create(formula, new UniformDistribution(formula.getAtoms()), ProgressionStrategy.LEAKY);
```

By implicit construction using a `String` representation:
```java
// Build an MTL formula and feed it to a progressor
String formulaStr = "always ( ( p ) or (eventually [0,100] ( always [0,10] p ) ) )";
MTLLexer lexer = new MTLLexer(new ANTLRInputStream(formulaStr));
MTLParser parser = new MTLParser(new CommonTokenStream(lexer));
ParseTree tree = parser.start();
Formula formula = new MTLVisitorImpl().visit(tree);
Progressor progressor = pf.create(formula, new UniformDistribution(formula.getAtoms()), ProgressionStrategy.LEAKY);
```

The MTL formula corresponds to `always ( not p -> (eventually [0,100] ( always [0,10] p ) ) )`.
Any partial states will be considered with a uniform probability in this example, but this behaviour can be customised.
The progressor receives its parameters from the progressor factory that generates it.
Note that the `Delta` parameter from Bacchus and Kabanza's classical progression procedure is fixed to 1 by default.
Finally, we need a stream generator.
The implementation provides a number of default generators for this purpose.
In this case, we'll go with the following:
```java
// Configure a stream generator with a stream pattern
StreamGenerator generator = StreamPatterns.createConstant("p", true, Integer.MAX_VALUE, 0.2, Main.SEED);
```
Here the `Integer.MAX_VALUE` corresponds to the maximum number of times a state is generated, so this generator produces an infinite-length stream.
The `0.2` corresponds to the fault ratio; this generator will produce an infinite stream of states `{{p}}`, but will produce a fully-unknown state `{{p},{}}` in 20% of the cases.
The `Main.SEED` value is used to repeat the experiments with the same random number generator seed.

In order to start progression, we use an executor:
```java
// Run partial-state progression
Executor executor = new Executor(progressor, generator, 0.99, path, false);
executor.start();

try {
    executor.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
```
This uses the `progressor` and `generator` we just created, and sets the termination criterion to correspond to 99% of probability mass residing in verdict nodes.
The `path` argument is used to write the results to a file.
The final argument is for verbosity, which we disabled here.

Adding timers and printing results leads to the following code:
```java
// Construct a progressor factory
long t1Start = System.nanoTime();
ProgressorFactory pf = new ProgressorFactory();
pf.setMaxNodes(maxNodes);
pf.setMaxTTL(1);

// Build an MTL formula and feed it to a progressor
String formulaStr = "always ( ( p ) or (eventually [0,100] ( always [0,10] p ) ) )";
MTLLexer lexer = new MTLLexer(new ANTLRInputStream(formulaStr));
MTLParser parser = new MTLParser(new CommonTokenStream(lexer));
ParseTree tree = parser.start();
Formula formula = new MTLVisitorImpl().visit(tree);
Progressor progressor = pf.create(formula, new UniformDistribution(formula.getAtoms()), ProgressionStrategy.LEAKY);

// Configure a stream generator with a stream pattern
StreamGenerator generator = StreamPatterns.createConstant("p", true, Integer.MAX_VALUE, 0.2, Main.SEED);
long t1End = System.nanoTime();
System.out.println("Formula: " + formula.toString());
System.out.println("Setup time: " + Math.round(((double)t1End - (double)t1Start)/1000.0/1000.0) + "ms\n");

// Run partial-state progression
Executor executor = new Executor(progressor, generator, 0.99, path, false);
executor.start();

try {
    executor.join();
} catch (InterruptedException e) {
    e.printStackTrace();
}
long tEnd = System.nanoTime();

System.out.println("RESULT");
System.out.println(progressor.getStatus());
System.out.println(progressor.getProperties());
System.out.println("Total iterations: " + executor.getIteration());
System.out.println("Total runtime: " + Math.round(((double)tEnd - (double)t1Start)/1000.0/1000.0) + "ms\n");
```


## Reproducing the Leakage Experiments (KR-2018)
In order to reproduce the leakage experiments illustrated in Figure 1 of our KR extended abstract, we have provided some useful scripts in the `scripts/` folder.
We will assume a Linux environment.
First, one has to build the project artifact using IntelliJ IDEA.
This will produce a JAR file at `out/artifacts/jprogressor_jar/jprogressor.jar`.
We can then run the experiments by running the following command inside the `scripts/` folder:
```console
$ sh run-leakage-experiments.sh
```
Note that this may take a while, so it might help to start this in a `screen` session.
Once the experiments have been completed, there will be a number of files called `kr-online-1-*-*` for values of `MAX_NODES` ranging from 5 to 190, and iterations 1 through 10.
To generate the graph shown in the extended abstract, one can use Matlab to run the `leakage_characteristics.m` file.
This should then generate a `leakage-characteristics.eps` file similar to the one shown in the extended abstract.
Variations in run-time are to be expected depending on the differences in the hardware used.


## License
The *jprogress* software is released under the MIT license.
