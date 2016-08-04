package function.genotype.trio;

import function.external.evs.EvsManager;
import function.genotype.base.SampleManager;
import function.external.exac.ExacManager;
import function.annotation.base.GeneManager;
import function.external.genomes.GenomesManager;
import function.external.gerp.GerpManager;
import function.external.kaviar.KaviarManager;
import function.external.knownvar.KnownVarManager;
import function.external.mgi.MgiManager;
import function.external.rvis.RvisManager;
import function.external.subrvis.SubRvisManager;
import function.external.trap.TrapManager;
import function.genotype.base.CalledVariant;
import function.genotype.base.Carrier;
import global.Data;
import global.Index;
import utils.FormatManager;
import utils.MathManager;

/**
 *
 * @author nick
 */
public class DenovoOutput extends TrioOutput {

    String flag = "";

    public static String getTitle() {
        return "Family ID,"
                + "Child,"
                + "Mother,"
                + "Father,"
                + "Flag,"
                + "Variant ID,"
                + "Variant Type,"
                + "Rs Number,"
                + "Ref Allele,"
                + "Alt Allele,"
                + "CADD Score Phred,"
                + GerpManager.getTitle()
                + TrapManager.getTitle()
                + "Is Minor Ref,"
                + "Genotype (child),"
                + "Samtools Raw Coverage (child),"
                + "Gatk Filtered Coverage (child),"
                + "Reads Alt (child),"
                + "Reads Ref (child),"
                + "Percent Read Alt (child),"
                + "Percent Alt Read Binomial P (child),"
                + "Pass Fail Status (child),"
                + "Genotype Qual GQ (child),"
                + "Qual By Depth QD (child),"
                + "Haplotype Score (child),"
                + "Rms Map Qual MQ (child),"
                + "Qual (child),"
                + "Genotype (mother),"
                + "Samtools Raw Coverage (mother),"
                + "Gatk Filtered Coverage (mother),"
                + "Reads Alt (mother),"
                + "Reads Ref (mother),"
                + "Percent Read Alt (mother),"
                + "Genotype (father),"
                + "Samtools Raw Coverage (father),"
                + "Gatk Filtered Coverage (father),"
                + "Reads Alt (father),"
                + "Reads Ref (father),"
                + "Percent Read Alt (father),"
                + "Major Hom Case,"
                + "Het Case,"
                + "Minor Hom Case,"
                + "Minor Hom Case Freq,"
                + "Het Case Freq,"
                + "Major Hom Ctrl,"
                + "Het Ctrl,"
                + "Minor Hom Ctrl,"
                + "Minor Hom Ctrl Freq,"
                + "Het Ctrl Freq,"
                + "Missing Case,"
                + "QC Fail Case,"
                + "Missing Ctrl,"
                + "QC Fail Ctrl,"
                + "Case MAF,"
                + "Ctrl MAF,"
                + "Case HWE_P,"
                + "Ctrl HWE_P,"
                + EvsManager.getTitle()
                + "Polyphen Humdiv Score,"
                + "Polyphen Humdiv Prediction,"
                + "Polyphen Humvar Score,"
                + "Polyphen Humvar Prediction,"
                + "Function,"
                + "Gene Name,"
                + "Artifacts in Gene,"
                + "Codon Change,"
                + "Gene Transcript (AA Change),"
                + ExacManager.getTitle()
                + KaviarManager.getTitle()
                + KnownVarManager.getTitle()
                + RvisManager.getTitle()
                + SubRvisManager.getTitle()
                + GenomesManager.getTitle()
                + MgiManager.getTitle();
    }

    public DenovoOutput(CalledVariant c) {
        super(c);
    }

    public String getFamilyId() {
        return familyId;
    }

    public String getFlag() {
        return flag;
    }

    public void initFlag(int id) {
        convertParentGeno();

        flag = TrioManager.getStatus(calledVar.getChrNum(),
                !isMinorRef, SampleManager.isMale(id),
                cGeno, cSamtoolsRawCoverage,
                mGeno, mSamtoolsRawCoverage,
                fGeno, fSamtoolsRawCoverage);
    }

    /*
     * convert all missing genotype to hom ref for parents
     */
    private void convertParentGeno() {
        if (mGeno == Data.NA) {
            mGeno = 0;
        }

        if (fGeno == Data.NA) {
            fGeno = 0;
        }
    }

    public void initGenoZygo(int childIndex) {
        cGenotype = getGenoStr(calledVar.getGenotype(childIndex));
        mGenotype = getGenoStr(mGeno);
        fGenotype = getGenoStr(fGeno);
    }

    public String getString(Trio trio) {
        StringBuilder sb = new StringBuilder();

        Carrier carrier = calledVar.getCarrier(trio.getChildId());

        sb.append(familyId).append(",");
        sb.append(childName).append(",");
        sb.append(motherName).append(",");
        sb.append(fatherName).append(",");
        sb.append(flag).append(",");
        sb.append(calledVar.getVariantIdStr()).append(",");
        sb.append(calledVar.getType()).append(",");
        sb.append(calledVar.getRsNumber()).append(",");
        sb.append(calledVar.getRefAllele()).append(",");
        sb.append(calledVar.getAllele()).append(",");
        sb.append(FormatManager.getDouble(calledVar.getCscore())).append(",");
        sb.append(calledVar.getGerpScore());
        sb.append(calledVar.getTrapScore());
        sb.append(isMinorRef).append(",");
        sb.append(cGenotype).append(",");
        sb.append(FormatManager.getDouble(cSamtoolsRawCoverage)).append(",");
        sb.append(FormatManager.getDouble(cGatkFilteredCoverage)).append(",");
        sb.append(FormatManager.getDouble(cReadsAlt)).append(",");
        sb.append(FormatManager.getDouble(cReadsRef)).append(",");
        sb.append(FormatManager.getPercAltRead(cReadsAlt, cGatkFilteredCoverage)).append(",");
        sb.append(FormatManager.getDouble(MathManager.getBinomial(cReadsAlt + cReadsRef, cReadsAlt, 0.5))).append(",");
        sb.append(carrier != null ? carrier.getPassFailStatus() : "NA").append(",");
        sb.append(FormatManager.getDouble(carrier != null ? carrier.getGenotypeQualGQ() : Data.NA)).append(",");
        sb.append(FormatManager.getDouble(carrier != null ? carrier.getQualByDepthQD() : Data.NA)).append(",");
        sb.append(FormatManager.getDouble(carrier != null ? carrier.getHaplotypeScore() : Data.NA)).append(",");
        sb.append(FormatManager.getDouble(carrier != null ? carrier.getRmsMapQualMQ() : Data.NA)).append(",");
        sb.append(FormatManager.getDouble(carrier != null ? carrier.getQual() : Data.NA)).append(",");
        sb.append(mGenotype).append(",");
        sb.append(FormatManager.getDouble(mSamtoolsRawCoverage)).append(",");
        sb.append(FormatManager.getDouble(mGatkFilteredCoverage)).append(",");
        sb.append(FormatManager.getDouble(mReadsAlt)).append(",");
        sb.append(FormatManager.getDouble(mReadsRef)).append(",");
        sb.append(FormatManager.getPercAltRead(mReadsAlt, mGatkFilteredCoverage)).append(",");
        sb.append(fGenotype).append(",");
        sb.append(FormatManager.getDouble(fSamtoolsRawCoverage)).append(",");
        sb.append(FormatManager.getDouble(fGatkFilteredCoverage)).append(",");
        sb.append(FormatManager.getDouble(fReadsAlt)).append(",");
        sb.append(FormatManager.getDouble(fReadsRef)).append(",");
        sb.append(FormatManager.getPercAltRead(fReadsAlt, fGatkFilteredCoverage)).append(",");
        sb.append(majorHomCount[Index.CASE]).append(",");
        sb.append(genoCount[Index.HET][Index.CASE]).append(",");
        sb.append(minorHomCount[Index.CASE]).append(",");
        sb.append(FormatManager.getDouble(minorHomFreq[Index.CASE])).append(",");
        sb.append(FormatManager.getDouble(hetFreq[Index.CASE])).append(",");
        sb.append(majorHomCount[Index.CTRL]).append(",");
        sb.append(genoCount[Index.HET][Index.CTRL]).append(",");
        sb.append(minorHomCount[Index.CTRL]).append(",");
        sb.append(FormatManager.getDouble(minorHomFreq[Index.CTRL])).append(",");
        sb.append(FormatManager.getDouble(hetFreq[Index.CTRL])).append(",");
        sb.append(genoCount[Index.MISSING][Index.CASE]).append(",");
        sb.append(calledVar.getQcFailSample(Index.CASE)).append(",");
        sb.append(genoCount[Index.MISSING][Index.CTRL]).append(",");
        sb.append(calledVar.getQcFailSample(Index.CTRL)).append(",");
        sb.append(FormatManager.getDouble(minorAlleleFreq[Index.CASE])).append(",");
        sb.append(FormatManager.getDouble(minorAlleleFreq[Index.CTRL])).append(",");
        sb.append(FormatManager.getDouble(hweP[Index.CASE])).append(",");
        sb.append(FormatManager.getDouble(hweP[Index.CTRL])).append(",");
        sb.append(calledVar.getEvsStr());
        sb.append(calledVar.getPolyphenHumdivScore()).append(",");
        sb.append(calledVar.getPolyphenHumdivPrediction()).append(",");
        sb.append(calledVar.getPolyphenHumvarScore()).append(",");
        sb.append(calledVar.getPolyphenHumvarPrediction()).append(",");
        sb.append(calledVar.getFunction()).append(",");
        sb.append("'").append(calledVar.getGeneName()).append("'").append(",");
        sb.append(FormatManager.getInteger(GeneManager.getGeneArtifacts(calledVar.getGeneName()))).append(",");
        sb.append(calledVar.getCodonChange()).append(",");
        sb.append(calledVar.getTranscriptSet()).append(",");
        sb.append(calledVar.getExacStr());
        sb.append(calledVar.getKaviarStr());
        sb.append(calledVar.getKnownVarStr());
        sb.append(calledVar.getRvis());
        sb.append(calledVar.getSubRvis());
        sb.append(calledVar.get1000Genomes());
        sb.append(calledVar.getMgi());

        return sb.toString();
    }
}
