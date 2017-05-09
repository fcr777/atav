package function.coverage.comparison;

import function.annotation.base.Gene;
import function.coverage.base.CoverageAnalysisBase;
import function.coverage.base.CoverageCommand;
import function.genotype.base.Sample;
import function.genotype.base.SampleManager;
import global.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import utils.CommonCommand;
import utils.ErrorManager;
import utils.FormatManager;
import utils.MathManager;

/**
 *
 * @author nick
 */
public abstract class CoverageComparisonBase extends CoverageAnalysisBase {

    BufferedWriter bwCoverageSummaryByGene = null;
    BufferedWriter bwGeneSummaryClean = null;

    final String coverageSummaryByGene = CommonCommand.outputPath + "coverage.summary.csv";
    final String cleanedGeneSummaryList = CommonCommand.outputPath + "coverage.summary.clean.csv";

    @Override
    public void initOutput() {
        try {
            super.initOutput();

            bwCoverageSummaryByGene = new BufferedWriter(new FileWriter(coverageSummaryByGene));
            bwCoverageSummaryByGene.write("Gene,Chr,AvgCase,AvgCtrl,CovDiff,Length");
            bwCoverageSummaryByGene.newLine();

            bwGeneSummaryClean = new BufferedWriter(new FileWriter(cleanedGeneSummaryList));
            bwGeneSummaryClean.write("Gene,Chr,OriginalLength,AvgCase,AvgCtrl,CovDiff,CleanedLength");
            bwGeneSummaryClean.newLine();
        } catch (Exception ex) {
            ErrorManager.send(ex);
        }
    }

    @Override
    public void closeOutput() {
        try {
            super.closeOutput();

            bwCoverageSummaryByGene.flush();
            bwCoverageSummaryByGene.close();
            bwGeneSummaryClean.flush();
            bwGeneSummaryClean.close();
        } catch (Exception ex) {
            ErrorManager.send(ex);
        }
    }

    @Override
    public void beforeProcessDatabaseData() {
        super.beforeProcessDatabaseData();

        if (!CoverageCommand.isLinear
                && (SampleManager.getCaseNum() == 0 || SampleManager.getCtrlNum() == 0)) {
            ErrorManager.print("Error: this function does not support to run with case only or control only sample file");
        }
    }

    @Override
    public void processGene(Gene gene) {
        super.processGene(gene);

        outputGeneSummary(gene);
    }

    private void outputGeneSummary(Gene gene) {
        try {
            float caseAvg = 0, ctrlAvg = 0;
            for (Sample sample : SampleManager.getList()) {
                if (sample.isCase()) {
                    caseAvg += geneSampleCoverage[gene.getIndex()][sample.getIndex()];
                } else {
                    ctrlAvg += geneSampleCoverage[gene.getIndex()][sample.getIndex()];
                }
            }

            caseAvg = MathManager.devide(caseAvg, SampleManager.getCaseNum());
            caseAvg = MathManager.devide(caseAvg, gene.getLength());
            ctrlAvg = MathManager.devide(ctrlAvg, SampleManager.getCtrlNum());
            ctrlAvg = MathManager.devide(ctrlAvg, gene.getLength());

            if (CoverageCommand.isMinCoverageFractionValid(caseAvg)
                    && CoverageCommand.isMinCoverageFractionValid(ctrlAvg)) {
                StringBuilder sb = new StringBuilder();
                sb.append(gene.getName()).append(",");
                sb.append(gene.getChr()).append(",");
                sb.append(FormatManager.getFloat(caseAvg)).append(",");
                sb.append(FormatManager.getFloat(ctrlAvg)).append(",");

                float covDiff = Data.NA;

                if (CoverageCommand.isRelativeDifference) {
                    covDiff = MathManager.relativeDiff(caseAvg, ctrlAvg);
                } else {
                    covDiff = MathManager.abs(caseAvg, ctrlAvg);
                }

                sb.append(FormatManager.getFloat(covDiff)).append(",");
                sb.append(gene.getLength());
                writeToFile(sb.toString(), bwCoverageSummaryByGene);
            }
        } catch (Exception ex) {
            ErrorManager.send(ex);
        }
    }
}
