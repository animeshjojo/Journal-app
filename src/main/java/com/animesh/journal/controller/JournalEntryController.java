//package com.animesh.journal.controller;
//
//import com.animesh.journal.Entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/journalv1")
//public class JournalEntryController {
//
//    private Map<Integer,JournalEntry> journals=new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getall(){
//        return new ArrayList<>(journals.values());
//    }
//
//    @PostMapping
//    public boolean journalentry(@RequestBody JournalEntry e){
//        journals.put(e.getId(),e);
//        return true;
//    }
//
//    @GetMapping("/id/{myid}")
//    public JournalEntry getbyid(@PathVariable Integer myid){
//        return journals.get(myid);
//    }
//
//    @PutMapping("/id/{myid}")
//    public String update(@PathVariable Integer myid,@RequestBody JournalEntry e){
//        journals.put(myid,e);
//        return "DONE";
//    }
//
//    @DeleteMapping("id/{myid}")
//    public String deletentry(@PathVariable Integer myid){
//        journals.remove(myid);
//        return "Entry Deleted";
//    }
//}
