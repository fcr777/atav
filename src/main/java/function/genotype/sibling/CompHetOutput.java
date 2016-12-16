package function.genotype.sibling;

import function.genotype.base.CalledVariant;
import function.variant.base.Output;
import function.genotype.base.Sample;
import utils.FormatManager;

/**
 *
 * @author nick
 */
public class CompHetOutput extends Output {

    public static String getTitle() {
        return "Family ID,"
                + "Mother,"
                + "Father,"
                + "Flag,"
                + "Child1,"
                + "Child1 Trio Comp Het Flag,"
                + "Child2,"
                + "Child2 Trio Comp Het Flag,"
                + initVarTitleStr("1")
                + initVarTitleStr("2");
    }

    private static String initVarTitleStr(String var) {
        String varTitle = getVariantDataTitle()
                + getAnnotationDataTitle()
                + getExternalDataTitle()
                + getGenoStatDataTitle()
                + "Child1 GT,"
                + "Child1 DP Bin,"
                + "Child2 GT,"
                + "Child2 DP Bin,";

        String[] list = varTitle.split(",");

        varTitle = "";

        for (String s : list) {
            varTitle += s + " (#" + var + "),";
        }

        return varTitle;
    }

    public CompHetOutput(CalledVariant c) {
        super(c);
    }

    public String getString(Sample child1, Sample child2) {
        StringBuilder sb = new StringBuilder();

        calledVar.getVariantData(sb);
        calledVar.getAnnotationData(sb);
        calledVar.getExternalData(sb);
        getGenoStatData(sb);

        sb.append(getGenoStr(calledVar.getGT(child1.getIndex()))).append(",");
        sb.append(FormatManager.getShort(calledVar.getDPBin(child1.getIndex()))).append(",");
        sb.append(getGenoStr(calledVar.getGT(child2.getIndex()))).append(",");
        sb.append(FormatManager.getShort(calledVar.getDPBin(child2.getIndex()))).append(",");

        return sb.toString();
    }
}
