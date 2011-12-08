package work.unit.greader;

import greader.entities.Category;
import greader.tools.GoogleReaderAdapterTools;
import junit.framework.TestCase;

/**
 * Author: Igor Usenko ( Igor.Usenko@teamodc.com )
 * Date: 08.12.2011
 */
public class GoogleReaderAdapterToolsCategoriesAndBranchesTest extends TestCase {

    public GoogleReaderAdapterToolsCategoriesAndBranchesTest(final String _name) {
        super(_name);
    }

    public void testEmptyCategoriesListGivesEmptyBranchName() throws Exception {
        Category[] categories = new Category[] {};

        assertEquals("", GoogleReaderAdapterTools.getBranch(categories));
    }

    public void testNullCategoriesListGivesEmptyBranchName() throws Exception {
        assertEquals("", GoogleReaderAdapterTools.getBranch(null));
    }

    public void testFirstBranchNameCandidateReturns() throws Exception {
        Category branch = new Category("first", "branch");
        Category criterion = new Category("second", GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion");
        Category anotherBranch = new Category("third", "anotherBranch");

        Category[] categories = new Category[] {criterion, branch, anotherBranch};

        assertEquals("branch", GoogleReaderAdapterTools.getBranch(categories));
    }

    public void testIfNoBranchNamesInListEmptyBranchNameReturns() throws Exception {
        Category criterion = new Category("second", GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion");
        Category anotherCriterion = new Category("third", GoogleReaderAdapterTools.CRITERION_PREFIX + "anotherCriterion");

        Category[] categories = new Category[] {criterion, anotherCriterion};

        assertEquals("", GoogleReaderAdapterTools.getBranch(categories));
    }

    public void testEmptyCategoriesListGivesEmptyCriterion() throws Exception {
        Category[] categories = new Category[] {};

        assertEquals("", GoogleReaderAdapterTools.getCriterion(categories));
    }

    public void testNullCategoriesListGivesEmptyCriterion() throws Exception {
        assertEquals("", GoogleReaderAdapterTools.getCriterion(null));
    }

    public void testFirstCriterionCandidateReturns() throws Exception {
        Category branch = new Category("first", "branch");
        Category criterion = new Category("second", GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion");
        Category anotherCriterion = new Category("third", GoogleReaderAdapterTools.CRITERION_PREFIX + "anotherCriterion");

        Category[] categories = new Category[] {criterion, branch, anotherCriterion};

        assertEquals(GoogleReaderAdapterTools.CRITERION_PREFIX + "criterion", GoogleReaderAdapterTools.getCriterion(categories));
    }

    public void testIfNoCriterionInListEmptyCriterionReturns() throws Exception {
        Category branch = new Category("first", "branch");
        Category anotherBranch = new Category("third", "anotherBranch");

        Category[] categories = new Category[] {branch, anotherBranch};

        assertEquals("", GoogleReaderAdapterTools.getCriterion(categories));
    }

}
