package org.openda.resultwriters;


import groovy.json.*
import org.openda.interfaces.IInstance
import org.openda.interfaces.IResultWriter
import org.openda.interfaces.IVector

class PlotlyResultWriter implements IResultWriter {

    def filePath = null

    /**
    * Put a message.
    *
    * @param source the producer of the message (algorithm, model or observer)
    * @param message message to be written to output
    */

    PlotlyResultWriter(File workingDir, String configString) {
        filePath = new File( workingDir.canonicalPath, configString)
    }

    void putMessage(IResultWriter.Source source, String message) {

    }
    /**
     * Put a message.
     *
     * @param source the producer of the message (algorithm, model or observer)
     * @param message message to be written to output
     */

    void putMessage(IInstance source, String message) {

    }

    /**
     * Put a vector, indicating the algorithm's current iteration.
     *
     * @param source the producer of the message (algorithm, model or observer)
     * @param id the output vector's identifier
     * @param result the output item
     * @param outputLevel the level of output group
     * @param context context where the value is written
     * @param iteration current iteration
     */

    void putValue(IResultWriter.Source source, String id, Object result, IResultWriter.OutputLevel outputLevel, String context, int iteration) {

    }

    /**
     * Put a report on the iteration.
     *
     * @param source the producer of the message (algorithm, model or observer)
     * @param iteration iteration index
     * @param cost cost function
     * @param parameters parameters used in the computation
     */

    void putIterationReport(IInstance source, int iteration, double cost, IVector parameters) {

        double[] parameterValues = parameters.getValues();
        def json = JsonOutput.toJson([name: 'John Doe', age: 42])
        new File(filePath).write(JsonOutput.prettyPrint(json))
    }

    /**
     * Give the default maximum size of result item that can be printed to the result file by this result writer.
     * @return int defaultMaxSize
     */

    int getDefaultMaxSize() {
        return 0
    } /**
     * Free the ResultWriter instance.
     */

    void free() {

    }
}
