package function.genotype.trio;

import function.genotype.base.CalledVariant;
import function.genotype.base.Carrier;
import function.variant.base.Output;
import function.genotype.base.Sample;
import global.Data;
import global.Index;
import function.genotype.base.SampleManager;

/**
 *
 * @author nick
 */
public class TrioOutput extends Output {

    // Trio Family data
    String familyId;
    String childName;
    static final String childType = "Case"; // Sample pheno type - child
    int childIndex;
    int cGeno;
    int cSamtoolsRawCoverage;
    int cGatkFilteredCoverage;
    int cReadsAlt;
    int cReadsRef;
    String cGenotype = "NA";
    String motherName;
    int mGeno;
    int mSamtoolsRawCoverage;
    int mGatkFilteredCoverage;
    int mReadsAlt;
    int mReadsRef;
    String mGenotype;
    String fatherName;
    int fGeno;
    int fSamtoolsRawCoverage;
    int fGatkFilteredCoverage;
    int fReadsAlt;
    int fReadsRef;
    String fGenotype;

    public TrioOutput(CalledVariant c) {
        super(c);
    }

    public void initTrioFamilyData(Trio trio) {
        familyId = trio.getFamilyId();

        childName = trio.getChildName();
        childIndex = trio.getChildIndex();
        cGeno = calledVar.getGenotype(trio.getChildIndex());
        cGenotype = getGenoStr(cGeno);
        cSamtoolsRawCoverage = calledVar.getCoverage(trio.getChildIndex());
        Carrier carrier = calledVar.getCarrier(trio.getChildId());
        cGatkFilteredCoverage = carrier != null ? carrier.getGatkFilteredCoverage() : Data.NA;
        cReadsAlt = carrier != null ? carrier.getReadsAlt() : Data.NA;
        cReadsRef = carrier != null ? carrier.getReadsRef() : Data.NA;

        motherName = trio.getMotherName();
        mGeno = calledVar.getGenotype(trio.getMotherIndex());
        mGenotype = getGenoStr(mGeno);
        mSamtoolsRawCoverage = calledVar.getCoverage(trio.getMotherIndex());
        carrier = calledVar.getCarrier(trio.getMotherId());
        mGatkFilteredCoverage = carrier != null ? carrier.getGatkFilteredCoverage() : Data.NA;
        mReadsAlt = carrier != null ? carrier.getReadsAlt() : Data.NA;
        mReadsRef = carrier != null ? carrier.getReadsRef() : Data.NA;

        fatherName = trio.getFatherName();
        fGeno = calledVar.getGenotype(trio.getFatherIndex());
        fGenotype = getGenoStr(fGeno);
        fSamtoolsRawCoverage = calledVar.getCoverage(trio.getFatherIndex());
        carrier = calledVar.getCarrier(trio.getFatherId());
        fGatkFilteredCoverage = carrier != null ? carrier.getGatkFilteredCoverage() : Data.NA;
        fReadsAlt = carrier != null ? carrier.getReadsAlt() : Data.NA;
        fReadsRef = carrier != null ? carrier.getReadsRef() : Data.NA;
    }

    public void deleteParentGeno(Trio trio) {
        deleteSampleGeno(trio.getMotherId());

        deleteSampleGeno(trio.getFatherId());
    }

    public void deleteSampleGeno(int id) {
        if (id != Data.NA) {
            Sample sample = SampleManager.getMap().get(id);

            int geno = calledVar.getGenotype(sample.getIndex());
            int type = getGenoType(geno, sample);

            deleteSampleGeno(type, sample.getPheno());

            genoCount[Index.MISSING][sample.getPheno()]++;
        }
    }

    public void addParentGeno(Trio trio) {
        addSampleGeno(trio.getMotherId());

        addSampleGeno(trio.getFatherId());
    }

    public void addSampleGeno(int id) {
        if (id != Data.NA) {
            Sample sample = SampleManager.getMap().get(id);

            int geno = calledVar.getGenotype(sample.getIndex());
            int type = getGenoType(geno, sample);

            addSampleGeno(type, sample.getPheno());

            genoCount[Index.MISSING][sample.getPheno()]--;
        }
    }
}
