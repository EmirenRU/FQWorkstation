/* eslint-disable no-unexpected-multiline */

import { z } from "zod";
import {  useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { registerRequest } from "../api/Send";
import { queryClient } from "../api/queryClient";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Button } from "../button/Button";
import "./support.css"
import { FormField } from "../FormField/FormField";

const phoneRegex = new RegExp(
    /^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$/
  );
    
    const createRegisterScheme = z.object({
        username: z.string().min(5,"Имя пользователя слишком короткое"),
        phone: z.string().regex(phoneRegex, 'Invalid Number!'),
        email: z.string().email(),
        message: z.string().min(4,"Длина сообщения слишком коротка"),
        agreement: z.boolean().refine((val) => val === true, {
            message: "поставьте галочку",
        })
      })
  
     type createRegisterForm = z.infer<typeof createRegisterScheme>;
  
     export function Support() {
      const {
        register,
        handleSubmit,
        formState: { errors },
      } = useForm<createRegisterForm>({
        resolver: zodResolver( createRegisterScheme ), // Use Zod resolver
      });
    
      const [errorState, setErrorState] = useState<string | null>(null);

  // Integrate useMutation
  const createRegisterMutation = useMutation({
    mutationFn: (data: createRegisterForm) =>
      registerRequest(data.username, data.phone, data.email, data.message, data.agreement),
    onSuccess: () => {
      alert("Form submitted successfully!");
      queryClient.invalidateQueries({ queryKey: ["users"] }); // Invalidate cache if needed
    },
    onError: (error: Error) => {
      setErrorState(error.message || "An error occurred during submission.");
    },
  }, queryClient);

  const onSubmit = (data: createRegisterForm) => {
    setErrorState(null); // Clear previous errors
    createRegisterMutation.mutate(data); // Trigger the mutation
  };
    

    return <><main>
    <div className="form-support container">
      <h3 className="form__header">Оставить заявку</h3>
      <form className="form-body-support" role="form"  onSubmit={handleSubmit(onSubmit)} >
        <div className="input">
        <FormField  errorMessage={errors.username?.message}>
          <input
                  type="text"
                  className="input__str"
                  placeholder="Фамилия, имя и отчество*"
                  required
                  aria-label="Фамилия, имя и отчество" id="fullName" 
                  autoComplete="name"
                  {...register("username")}></input>
          </FormField>
          <FormField  errorMessage={errors.phone?.message}>
          <input
                  type="tel"
                  className="input__str"
                  placeholder="Введите номер телефона"
                  required
                  aria-label="Введите номер телефона" id="phone" 
                  autoComplete="tel"
                  {...register("phone")}></input>
                    </FormField>

                    <FormField  errorMessage={errors.email?.message}>
          <input
                  type="email"
                  className="input__str"
                  placeholder="Email*"
                  required id="email" 
                  autoComplete="email"
                  {...register("email")}></input>
                            </FormField>
        <FormField errorMessage={errors.message?.message}>
            <textarea
              className="input__str-msg msg-box"
              placeholder="Сообщение"
              id="description"
              {...register("message")}
          ></textarea>
          </FormField>
        </div>
        {errorState && <span style={{ color: "red" }}>{errorState}</span>}
        <div className="send flex">
          <Button type="submit" className="btn-reset send__button"> <span className="send__text">Отправить данные</span></Button>

          <div className="custom">
          <FormField errorMessage={errors.agreement?.message}>
    <label className="custom-checkbox"> 
        <input
            type="checkbox"
            {...register("agreement")}
            className="custom-input"
            role="checkbox"
            aria-checked="false"
        />
        <span className="custom-text">Согласен на обработку данных</span>
    </label>
</FormField>
          </div>
        </div>
      </form>
    </div>
</main></>
     }
