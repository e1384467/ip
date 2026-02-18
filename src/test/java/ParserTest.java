import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import jerry.exceptions.JerryException;
import jerry.exceptions.MissingArgumentException;
import jerry.exceptions.WrongArgumentException;
import jerry.parser.Parser;
import jerry.task.Deadline;
import jerry.task.Event;
import jerry.task.ToDo;

public class ParserTest {

    // ============ ToDo Tests ============
    @Test
    public void parseTodo_success() throws JerryException {
        assertEquals(new ToDo("Sleep").toString(), Parser.parseTodo("Sleep").toString());
        assertEquals(new ToDo("Eat").toString(), Parser.parseTodo("Eat").toString());
    }

    @Test
    public void parseTodo_emptyUserInput_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.parseTodo(""));
    }

    @Test
    public void parseTodo_userInputContainsPipe_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () -> Parser.parseTodo("eat |"));
    }

    @Test
    public void parseTodo_singleCharacter_success() throws JerryException {
        assertEquals(new ToDo("A").toString(), Parser.parseTodo("A").toString());
    }

    @Test
    public void parseTodo_withLeadingTrailingSpaces_success() throws JerryException {
        assertEquals(new ToDo("  Sleep  ").toString(), Parser.parseTodo("  Sleep  ").toString());
    }

    // ============ Deadline Tests ============
    @Test
    public void parseDeadline_success() throws JerryException {
        assertEquals(new Deadline("Sleep",
                        LocalDateTime.parse("06062002 0540",
                                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"))).toString(),
                Parser.parseDeadline("Sleep /by 06062002 0540").toString());
    }

    @Test
    public void parseDeadline_caseInsensitiveDelimiter() throws JerryException {
        LocalDateTime expected = LocalDateTime.parse("06062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        assertEquals(new Deadline("Sleep", expected).toString(),
                Parser.parseDeadline("Sleep /By 06062002 0540").toString());
        assertEquals(new Deadline("Sleep", expected).toString(),
                Parser.parseDeadline("Sleep /bY 06062002 0540").toString());
        assertEquals(new Deadline("Sleep", expected).toString(),
                Parser.parseDeadline("Sleep /BY 06062002 0540").toString());
    }

    @Test
    public void parseDeadline_userInputContainsPipe_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () -> Parser.parseDeadline("Sleep | /by 06062002 0540"));
    }

    @Test
    public void parseDeadline_emptyTaskField_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.parseDeadline("/by 06062002 1300"));
    }

    @Test
    public void parseDeadline_emptyByField_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.parseDeadline("Sleep"));
    }

    @Test
    public void parseDeadline_emptyUserInput_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.parseDeadline(""));
    }

    @Test
    public void parseDeadline_wrongDateTimeFormat_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () -> Parser.parseDeadline("Sleep /by 06062002-0540"));
    }

    @Test
    public void parseDeadline_invalidDay_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () -> Parser.parseDeadline("Sleep /by 32062002 0540"));
    }

    @Test
    public void parseDeadline_invalidHour_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () -> Parser.parseDeadline("Sleep /by 06062002 2500"));
    }

    @Test
    public void parseDeadline_extraSpacesAroundDelimiter() throws JerryException {
        LocalDateTime expected = LocalDateTime.parse("06062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        assertEquals(new Deadline("Sleep", expected).toString(),
                Parser.parseDeadline("Sleep   /by   06062002 0540").toString());
    }

    // ============ Event Tests ============
    @Test
    public void parseEvent_success() throws JerryException {
        LocalDateTime from = LocalDateTime.parse("06062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        LocalDateTime to = LocalDateTime.parse("07062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        assertEquals(new Event("Sleep", from, to).toString(),
                Parser.parseEvent("Sleep /from 06062002 0540 /to 07062002 0540").toString());
    }

    @Test
    public void parseEvent_caseInsensitiveDelimiter() throws JerryException {
        LocalDateTime from = LocalDateTime.parse("06062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        LocalDateTime to = LocalDateTime.parse("07062002 0540",
                DateTimeFormatter.ofPattern("ddMMyyyy HHmm"));
        assertEquals(new Event("Sleep", from, to).toString(),
                Parser.parseEvent("Sleep /FroM 06062002 0540 /tO 07062002 0540").toString());
    }

    @Test
    public void parseEvent_userInputContainsPipe_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () ->
                Parser.parseEvent("Sleep | /from 06062002 0540 /to 07062002 0540"));
    }

    @Test
    public void parseEvent_emptyTaskField_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () ->
                Parser.parseEvent("/from 06062002 0540 /to 07062002 0540"));
    }

    @Test
    public void parseEvent_emptyFromField_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () ->
                Parser.parseEvent("Sleep /to 07062002 0540"));
    }

    @Test
    public void parseEvent_emptyToField_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () ->
                Parser.parseEvent("Sleep /from 06062002 0540"));
    }

    @Test
    public void parseEvent_emptyUserInput_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.parseEvent(""));
    }

    @Test
    public void parseEvent_invalidTimeFrame_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () ->
                Parser.parseEvent("Sleep /from 07062002 0540 /to 06062002 0540"));
    }

    @Test
    public void parseEvent_wrongDateTimeFormat_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () ->
                Parser.parseEvent("Sleep /from 06062002-0540 /to 07062002T0540"));
    }

    // ============ Array Index Tests ============
    @Test
    public void getArrayIndex_success() throws JerryException {
        assertEquals(-1, Parser.getArrayIndex(Parser.getUserInputArguments("Mark 0")));
        assertEquals(0, Parser.getArrayIndex(Parser.getUserInputArguments("Mark 1")));
        assertEquals(1, Parser.getArrayIndex(Parser.getUserInputArguments("Mark 2")));
        assertEquals(2, Parser.getArrayIndex(Parser.getUserInputArguments("Mark 3")));
    }

    @Test
    public void getArrayIndex_notANumber_wrongArgumentExceptionThrown() {
        assertThrows(WrongArgumentException.class, () ->
                Parser.getArrayIndex(Parser.getUserInputArguments("Mark task1")));
    }

    @Test
    public void getArrayIndex_missingUserInputs_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () ->
                Parser.getArrayIndex(Parser.getUserInputArguments("")));
    }

    @Test
    public void getArrayIndex_largeNumber_success() throws JerryException {
        assertEquals(999, Parser.getArrayIndex("1000"));
    }

    // ============ Search Query Tests ============
    @Test
    public void getSearchQuery_success() throws JerryException {
        assertEquals("book", Parser.getSearchQuery("book"));
        assertEquals("multiple words search", Parser.getSearchQuery("multiple words search"));
    }

    @Test
    public void getSearchQuery_emptyQuery_missingArgumentExceptionThrown() {
        assertThrows(MissingArgumentException.class, () -> Parser.getSearchQuery(""));
    }

    // ============ Input Parsing Tests ============
    @Test
    public void getUserInputCommand_success() {
        assertEquals("Mark", Parser.getUserInputCommand("Mark 1"));
        assertEquals("todo", Parser.getUserInputCommand("todo sleep"));
    }

    @Test
    public void getUserInputArguments_success() {
        assertEquals("1", Parser.getUserInputArguments("Mark 1"));
        assertEquals("sleep", Parser.getUserInputArguments("todo sleep"));
    }

    @Test
    public void getUserInputArguments_noArguments() {
        assertEquals("", Parser.getUserInputArguments("Mark"));
    }
}
