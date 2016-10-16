(ns milestones.parser-rules)

(def rules
  [;;Rule 0 milestone 5 : goal reached  when tasks 2,3 are complete.
   '(:task-id
     #{#{:Milestone}}
     #{#{:Noun :Value}}
     :task-name
     #{#{:Noun}}
     #{#{:Adjective} #{:Verb :PastTense}}
     :predecessors
     #{#{:Question}}
     #{#{:Task}}
     #{#{:Noun :Value}}
     #{#{:Noun}}
     #{#{:Adjective}})
   ;;Rule 1: When task 1,2 and 3 are achieved Rafik can work 3 minutes on task 4 in order to Eat bread
   '( :predecessors
     #{#{ :Question}}
     #{#{ :Task}}
     #{#{ :Noun :Value}}
     #{#{ :Noun}}
     #{#{ :Verb :PastTense}}
      :resource-id
     #{#{ :Noun}}
      :duration
     #{#{ :Verb :Modal}}
     #{#{ :Verb :Infinitive}}
     #{#{ :Noun :Plural :Date}}
      :task-id
     #{#{ :Preposition}}
     #{#{ :Task}}
     #{#{ :Noun :Value}}
      :task-name
     #{#{ :Preposition}}
     #{#{ :Noun}}
     #{#{ :Preposition}}
     #{#{ :Verb :Infinitive}}
     #{#{ :Noun}}
     )
   ;;Rule 2: for task 1  Rafik will have to eat bread in 2 minutes, after  tasks 3,2 and 15.
   '( :task-id
     #{#{ :Conjunction}}
     #{#{ :Task}}
     #{#{ :Noun :Value}}
      :resource-id
     #{#{ :Noun}}
      :task-name
     #{#{ :Verb :FutureTense}}
     #{#{ :Preposition}}
     #{#{ :Verb :Infinitive}}
     #{#{ :Noun}}
      :duration
     #{#{ :Preposition}}
     #{#{ :Noun :Plural :Date}}
      :dependencies
     #{#{ :Preposition :Condition}}
     #{#{ :Task :Condition}}
     #{#{ :Noun :Value :Condition}})
   
   ;; Rule 3 - "task 1 : Rafik shall eat bread in 2 minutes, after  task 3,2 and 15." 
   '( :task-id
     #{#{ :Task}}
     #{#{ :Noun :Value}}
      :resource-id
     #{#{ :Noun}}
      :task-name
     #{#{ :Verb :Modal}}
     #{#{ :Verb :Infinitive}}
     #{#{ :Noun}}
      :duration
     #{#{ :Preposition}}
     #{#{ :Noun :Plural :Date}}
      :predecessors
     #{#{ :Preposition :Condition}}
     #{#{ :Task :Condition}}
     #{#{ :Noun :Value :Condition}})])

(defn item-significant-value?
  [input-item step]
  (let [input-item-tags (get input-item 1)]
    (cond
      (or  (contains? input-item-tags :FutureTense)
           (contains? input-item-tags :Preposition)
           (contains? input-item-tags :Milestone)
           (and (= step :predecessors) (or (contains? input-item-tags :PastTense)
                                        (contains? input-item-tags :Adjective)
                                           (=  (set  (keys input-item-tags)) #{:Noun})
                                            ) )
           (contains? input-item-tags :Task)
           (contains? input-item-tags :Conjunction)
           (contains? input-item-tags :Question)) false
     
      :default true)))
