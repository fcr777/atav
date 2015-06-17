package global;

/**
 *
 * @author qwang
 */
public class SqlQuery {

    public static String EVS_MAF_SNV
            = "SELECT MAF_perc, ea_allele_count, aa_allele_count,all_allele_count, "
            + "ea_genotype_count, aa_genotype_count, all_genotype_count,ref_allele, alt_alleles, FilterStatus "
            + "FROM evs.snv_maf_new "
            + "WHERE chr = '_CHR_' AND position = _POS_ AND ref_allele = '_ALLELE_' ";
    public static String EVS_MAF_INDEL
            = "SELECT MAF_perc, ea_allele_count, aa_allele_count,all_allele_count, "
            + "ea_genotype_count, aa_genotype_count, all_genotype_count,ref_allele, alt_alleles, FilterStatus  "
            + "FROM evs.indel_maf_new "
            + "WHERE chr = '_CHR_' AND position = _POS_ ";
    public static String EVS_COVERAGE
            = "SELECT ALLSampleCovered, AllAvgCoverage, "
            + "EASampleCovered, EAAvgCoverage, "
            + "AASampleCovered, AAAvgCoverage "
            + "FROM evs.coverage "
            + "WHERE chr = '_CHR_' AND position = _POS_ ";

    public static String EVS_COVERAGE_RANGE
            = "SELECT position, ALLSampleCovered, EASampleCovered, AASampleCovered "
            + "FROM evs.coverage "
            + "WHERE chr = '_CHR_' AND position >= _POS1_ AND position <= _POS2_ ";

    public static String GENE_EXON_NAME = "SELECT e.exon_id, e.seq_region_id, e.seq_region_start, "
            + "e.seq_region_end, e.stable_id, r.name,t.stable_id as t_stable_id "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.exon_transcript et, _DB_HSC_.transcript t, "
            + "_DB_HSC_.object_xref ox, _DB_HSC_.xref x, _DB_HSC_.gene g,_DB_HSC_.seq_region r "
            + "WHERE e.exon_id = et.exon_id " + "AND et.transcript_id = t.transcript_id "
            + "AND t.gene_id = ox.ensembl_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND ox.ensembl_object_type = 'Gene' "
            + "AND ox.xref_id = x.xref_id "
            + "AND external_db_id = 1100 "
            + "AND display_label = '_GENE_' "
            + "AND r.seq_region_id = e.seq_region_id "
            + "AND t.stable_id like 'ENST%' ";
    public static String GENE_EXON_STABLEID = "SELECT e.exon_id, e.seq_region_id, "
            + "e.seq_region_start, e.seq_region_end, e.stable_id,r.name,t.stable_id as t_stable_id  "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.exon_transcript et,"
            + "_DB_HSC_.transcript t, _DB_HSC_.gene g,_DB_HSC_.seq_region r "
            + "WHERE e.exon_id = et.exon_id "
            + "AND et.transcript_id = t.transcript_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND t.gene_id = g.gene_id "
            + "AND g.stable_id = '_GENE_' "
            + "AND r.seq_region_id = e.seq_region_id ";
    public static String GENE_EXON_TRANSCRIPTID = "SELECT e.exon_id, e.seq_region_id, "
            + "e.seq_region_start, e.seq_region_end, e.stable_id,r.name,t.stable_id as t_stable_id  "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.exon_transcript et,"
            + "_DB_HSC_.transcript t, _DB_HSC_.seq_region r "
            + "WHERE e.exon_id = et.exon_id "
            + "AND et.transcript_id = t.transcript_id "
            + "AND t.transcript_id = _TRANSCRIPTID_ "
            + "AND r.seq_region_id = e.seq_region_id ";
    public static String GENE_UTR_TRANSCRIPTID = "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_start + trans.seq_start - 1) * (e.exon_id = trans.start_exon_id) * (g.seq_region_strand = 1) + "
            + "(e.seq_region_end - trans.seq_start + 1) * (e.exon_id = trans.start_exon_id) * (g.seq_region_strand = -1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox, _DB_HSC_.xref x, _DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.start_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = _TRANSCRIPTID_ "
            + "AND t.gene_id = ox.ensembl_id "
            + "AND ox.ensembl_object_type = 'Gene' "
            + "AND ox.xref_id = x.xref_id "
            + "AND external_db_id = 1100 "
            + "AND display_label = '_GENE_' "
            + "AND t.stable_id like 'ENST%') "
            + "union "
            + "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_end - trans.seq_end + 1) * (e.exon_id = trans.end_exon_id) * (g.seq_region_strand = -1) + "
            + "(e.seq_region_start + trans.seq_end - 1) * (e.exon_id = trans.end_exon_id) * (g.seq_region_strand = 1)  as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox, _DB_HSC_.xref x, _DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.end_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = _TRANSCRIPTID_ "
            + "AND t.gene_id = ox.ensembl_id "
            + "AND ox.ensembl_object_type = 'Gene' "
            + "AND ox.xref_id = x.xref_id "
            + "AND external_db_id = 1100 "
            + "AND display_label = '_GENE_' "
            + "AND t.stable_id like 'ENST%') "
            + "ORDER BY pos ";
    public static String GENE_UTR_NAME = "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_start + trans.seq_start - 1) * (e.exon_id = trans.start_exon_id) * (g.seq_region_strand = 1) + "
            + "(e.seq_region_end - trans.seq_start + 1) * (e.exon_id = trans.start_exon_id) * (g.seq_region_strand = -1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox, _DB_HSC_.xref x, _DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.start_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.gene_id = ox.ensembl_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND ox.ensembl_object_type = 'Gene' "
            + "AND ox.xref_id = x.xref_id "
            + "AND external_db_id = 1100 "
            + "AND display_label = '_GENE_' "
            + "AND t.stable_id like 'ENST%') "
            + "union "
            + "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_end - trans.seq_end + 1) * (e.exon_id = trans.end_exon_id) * (g.seq_region_strand = -1) + "
            + "(e.seq_region_start + trans.seq_end - 1) * (e.exon_id = trans.end_exon_id) * (g.seq_region_strand = 1)  as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox, _DB_HSC_.xref x, _DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.end_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.gene_id = ox.ensembl_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND ox.ensembl_object_type = 'Gene' "
            + "AND ox.xref_id = x.xref_id "
            + "AND external_db_id = 1100 "
            + "AND display_label = '_GENE_' "
            + "AND t.stable_id like 'ENST%') "
            + "ORDER BY pos ";
    public static String GENE_UTR_STABLEID = "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_start + trans.seq_start - 1)*(e.exon_id = trans.start_exon_id) *(g.seq_region_strand = 1) + "
            + "(e.seq_region_end - trans.seq_start + 1)*(e.exon_id = trans.start_exon_id)*(g.seq_region_strand = -1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,_DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.start_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND t.gene_id = g.gene_id "
            + "AND g.stable_id = '_GENE_') "
            + "union "
            + "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_end - trans.seq_end + 1)*(e.exon_id = trans.end_exon_id)*(g.seq_region_strand = -1) + "
            + "(e.seq_region_start + trans.seq_end - 1)*(e.exon_id = trans.end_exon_id)*(g.seq_region_strand = 1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,_DB_HSC_.gene g "
            + "WHERE (e.exon_id = trans.end_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = g.canonical_transcript_id "
            + "AND t.gene_id = g.gene_id "
            + "AND g.stable_id = '_GENE_') "
            + "ORDER BY pos ";

    public static String TRANSCRIPT_EXON_STABLEID = "SELECT e.exon_id, e.seq_region_id, e.seq_region_start, e.seq_region_end, e.stable_id, r.name,t.stable_id as t_stable_id "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.exon_transcript et,_DB_HSC_.transcript t, _DB_HSC_.seq_region r "
            + "WHERE e.exon_id = et.exon_id "
            + "AND et.transcript_id = t.transcript_id "
            + "AND t.stable_id = '_TRANSCRIPT_' "
            + "AND r.seq_region_id = e.seq_region_id ";
    public static String TRANSCRIPT_EXON_CCDSID = "SELECT e.exon_id, e.seq_region_id, e.seq_region_start, e.seq_region_end,  e.stable_id,name,t.stable_id as t_stable_id "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.exon_transcript et, "
            + "_DB_HSC_.transcript t,_DB_HSC_.object_xref ox, "
            + "_DB_HSC_.xref x, _DB_HSC_.seq_region r, _DB_HSC_.analysis a "
            + "WHERE e.exon_id = et.exon_id "
            + "AND e.seq_region_id = r.seq_region_id "
            + "AND et.transcript_id = t.transcript_id "
            + "AND t.transcript_id = ox.ensembl_id "
            + "AND ox.xref_id = x.xref_id "
            + "AND ox.ensembl_object_type = 'Transcript' "
            + "AND t.analysis_id = a.analysis_id "
            + "AND a.logic_name = 'ensembl' "
            + "AND external_db_id = 3800 "
            + "AND display_label = '_TRANSCRIPT_' ";
    public static String TRANSCRIPT_UTR_STABLEID = "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_start + trans.seq_start - 1)*(e.exon_id = trans.start_exon_id) *(t.seq_region_strand = 1) + "
            + "(e.seq_region_end - trans.seq_start + 1)*(e.exon_id = trans.start_exon_id)*(t.seq_region_strand = -1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t "
            + "WHERE (e.exon_id = trans.start_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.stable_id = '_TRANSCRIPT_') "
            + "union "
            + "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_end - trans.seq_end + 1)*(e.exon_id = trans.end_exon_id)*(t.seq_region_strand = -1) + "
            + "(e.seq_region_start + trans.seq_end - 1)*(e.exon_id = trans.end_exon_id)*(t.seq_region_strand = 1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t "
            + "WHERE (e.exon_id = trans.end_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.stable_id = '_TRANSCRIPT_') "
            + "ORDER BY pos ";
    public static String TRANSCRIPT_UTR_CCDSID = "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_start + trans.seq_start - 1)*(e.exon_id = trans.start_exon_id) *(t.seq_region_strand = 1) + "
            + "(e.seq_region_end - trans.seq_start + 1)*(e.exon_id = trans.start_exon_id)*(t.seq_region_strand = -1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox,_DB_HSC_.xref x, _DB_HSC_.analysis a "
            + "WHERE (e.exon_id = trans.start_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = ox.ensembl_id "
            + "AND ox.xref_id = x.xref_id "
            + "AND ox.ensembl_object_type = 'Transcript' "
            + "AND t.analysis_id = a.analysis_id "
            + "AND a.logic_name = 'ensembl' "
            + "AND external_db_id = 3800 "
            + "AND display_label = '_TRANSCRIPT_') "
            + "union "
            + "(SELECT e.exon_id, e.seq_region_id, "
            + "(e.seq_region_end - trans.seq_end + 1)*(e.exon_id = trans.end_exon_id)*(t.seq_region_strand = -1) + "
            + "(e.seq_region_start + trans.seq_end - 1)*(e.exon_id = trans.end_exon_id)*(t.seq_region_strand = 1) as pos "
            + "FROM _DB_HSC_.exon e, _DB_HSC_.translation trans, _DB_HSC_.transcript t,"
            + "_DB_HSC_.object_xref ox,_DB_HSC_.xref x, _DB_HSC_.analysis a "
            + "WHERE (e.exon_id = trans.end_exon_id) "
            + "AND trans.transcript_id = t.transcript_id "
            + "AND t.transcript_id = ox.ensembl_id "
            + "AND ox.xref_id = x.xref_id "
            + "AND ox.ensembl_object_type = 'Transcript' "
            + "AND t.analysis_id = a.analysis_id "
            + "AND a.logic_name = 'ensembl' "
            + "AND external_db_id = 3800 "
            + "AND display_label = '_TRANSCRIPT_') "
            + "ORDER BY pos ";

    public static String Region_Coverage_1024 = "SELECT sample_id, position, min_coverage FROM "
            //+ "_SAMPLE_TYPE__read_coverage_1024_test_chr_CHROM_ c ,"
            + "_SAMPLE_TYPE__read_coverage_1024_chr_CHROM_ c ,"
            + Data.ALL_SAMPLE_ID_TABLE + " t "
            + "WHERE position in (_POSITIONS_) "
            + "AND c.sample_id = t.id ";

    public static String Region_Coverage = " SELECT * FROM _SAMPLE_TYPE__read_coverage_chr_CHROM_ "
            + ", " + Data.ALL_SAMPLE_ID_TABLE + " t "
            + "WHERE "
            + "seq_region_pos >= IF(_START_ - _REGIONLENGTH_ - 1 > 0,_START_ - _REGIONLENGTH_ - 1,0) "
            + "AND seq_region_pos <= _END_ + _REGIONLENGTH_ + 1 "
            + "AND seq_region_pos <= _END_ + _REGIONLENGTH_ + 1 - region_length "
            + "AND sample_id = t.id "
            + "AND min_coverage >= _MIN_COV_ ";
    public static String GENE_CHR = "SELECT name "
            + "FROM _VAR_TYPE__gene_hit g, _VAR_TYPE_ v, seq_region r "
            + "WHERE g.gene_name = '_GENE_' "
            + "AND g._VAR_TYPE__id = v._VAR_TYPE__id "
            + "AND v.seq_region_id = r.seq_region_id "
            + "AND coord_system_id = 2 "
            + "LIMIT 1";
}
